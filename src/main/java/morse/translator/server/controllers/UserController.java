package morse.translator.server.controllers;

import morse.translator.server.dbms.models.Password;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.PasswordRepository;
import morse.translator.server.dbms.repositories.UserRepository;
import morse.translator.server.dbms.services.PasswordService;
import morse.translator.server.dbms.services.UserService;
import morse.translator.server.utils.logger.LogType;
import morse.translator.server.utils.logger.LoggerUtil;
import morse.translator.server.utils.security.CryptoUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * <p>Controller for processing requests user`s manipulating commands</p>
 * Supported operations: registration, enter, update user, delete user
 *
 * @author  Artem Bakanov aka Attilene
 */
@RestController
public class UserController {
    private static final Logger LOGGER_CONTROLLER = LoggerUtil.getLogger(LogType.CONTROLLER);
    private static final Logger LOGGER_ERROR = LoggerUtil.getLogger(LogType.ERROR);

    /**
     * Repository for manipulating data in the users table
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Repository for manipulating data in the passwords table
     */
    @Autowired
    private PasswordRepository passwordRepository;

    /**
     * Method for processing post-requests of user entering to private cabinet
     * <p>API: POST:/enter</p>
     *
     * @param   login_email  user login or email
     * @param   password     user password
     * @return               instance of User model class or null, if user instance does not exist
     */
    @PostMapping("/enter")
    public ResponseEntity<User> enterUser(@RequestParam String login_email,
                                          @RequestParam String password) {
        UserService userService = new UserService(userRepository);
        User user = userService.getByEmail(login_email);
        if (user == null) user = userService.getByLogin(login_email);
        if (user != null) {
            Password password_hash = new PasswordService(passwordRepository).getPasswordByUser(user);
            if (CryptoUtil.checkPassword(password, password_hash.getHash())) {
                LOGGER_CONTROLLER.info("Sign in is allowed for the user with id " + user.getId());
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        LOGGER_ERROR.error("Failed sign in try");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * Method for processing post-requests of creating new user`s private cabinet (registration)
     * <p>API: POST:/registration</p>
     *
     * @param   first_name    user first name
     * @param   last_name     user last name
     * @param   login         user login
     * @param   email         user email
     * @param   phone_number  user phone number
     * @param   birthday      user birthday
     * @param   password      user password
     * @return                "registration_success" string or "registration_failed" string, if user registration failed
     */
    @PostMapping("/registration")
    public ResponseEntity<String> registrationUser(@RequestParam String first_name,
                                                   @RequestParam String last_name,
                                                   @RequestParam String login,
                                                   @RequestParam String email,
                                                   @RequestParam String phone_number,
                                                   @RequestParam String birthday,
                                                   @RequestParam String password) {
        UserService userService = new UserService(userRepository);
        try {
            User user = new User();
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            user.setLogin(login);
            user.setEmail(email);
            if (phone_number.equals("null")) phone_number = null;
            user.setPhone_number(phone_number);
            try {
                user.setBirthday(Date.valueOf(birthday));
            } catch (IllegalArgumentException e) {
                user.setBirthday(null);
            }
            userService.addUser(user);
            byte[] salt = CryptoUtil.createSalt();
            Password password_hash = new Password(CryptoUtil.createHash(password, salt), CryptoUtil.toHex(salt));
            password_hash.setUser(user);
            new PasswordService(passwordRepository).addPassword(password_hash);
            LOGGER_CONTROLLER.info("Sign up successful for the user with id " + user.getId());
            return new ResponseEntity<>("registration_success", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER_ERROR.error("Failed sign up try", e);
            return new ResponseEntity<>("registration_failed", HttpStatus.OK);
        }
    }

    /**
     * Method for processing put-requests of updating user personal data
     * <p>API: PUT:/user</p>
     *
     * @param   id            user id
     * @param   first_name    user first name
     * @param   last_name     user last name
     * @param   login         user login
     * @param   email         user email
     * @param   phone_number  user phone number
     * @param   birthday      user birthday
     * @param   password      user password
     * @return                instance of User model class or null, if user instance does not exist
     */
    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@RequestParam Long id,
                                           @RequestParam String first_name,
                                           @RequestParam String last_name,
                                           @RequestParam String login,
                                           @RequestParam String email,
                                           @RequestParam String phone_number,
                                           @RequestParam String birthday,
                                           @RequestParam String password) {
        UserService userService = new UserService(userRepository);
        PasswordService passwordService = new PasswordService(passwordRepository);
        try {
            User user = userService.getById(id);
            if (user.getLogin() == null) throw new Exception();
            Password password_hash = passwordService.getPasswordByUser(user);
            byte[] salt = CryptoUtil.createSalt();
            password_hash.setHash(CryptoUtil.createHash(password, salt));
            password_hash.setSalt(CryptoUtil.toHex(salt));
            passwordService.updatePassword(password_hash);
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            user.setLogin(login);
            user.setEmail(email);
            if (phone_number.equals("null")) phone_number = null;
            user.setPhone_number(phone_number);
            try {
                user.setBirthday(Date.valueOf(birthday));
            } catch (IllegalArgumentException e) {
                user.setBirthday(null);
            }
            user.setPassword(password_hash);
            User outUser = userService.updateUser(user);
            LOGGER_CONTROLLER.info("Successful personal data update for the user with id " + id);
            return new ResponseEntity<>(outUser, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER_ERROR.error("Failed personal data update for the user with id " + id, e);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    /**
     * Method for processing delete-requests of deleting user`s private cabinet
     * <p>API: DELETE:/user</p>
     *
     * @param   id        user id
     * @param   password  user password
     * @return            "delete_success" or "delete_failed", if user delete failed
     */
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam Long id,
                                             @RequestParam String password) {
        UserService userService = new UserService(userRepository);
        try {
            User user = userService.getById(id);
            if (!CryptoUtil.checkPassword(password, user.getPassword().getHash())) throw new Exception();
            userService.deleteUser(user);
            LOGGER_CONTROLLER.info("Successful deleting the user with id " + id);
            return new ResponseEntity<>("delete_success", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER_ERROR.error("Failed deleting the user with id " + id, e);
            return new ResponseEntity<>("delete_failed", HttpStatus.OK);
        }
    }
}
