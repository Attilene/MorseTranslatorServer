package morse.translator.server.controllers;

import morse.translator.server.dbms.models.Password;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.PasswordRepository;
import morse.translator.server.dbms.repositories.UserRepository;
import morse.translator.server.dbms.services.PasswordService;
import morse.translator.server.dbms.services.UserService;
import morse.translator.server.utils.logger.LogType;
import morse.translator.server.utils.logger.LoggerUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
public class UserController {
    private static final Logger LOGGER_CONTROLLER = LoggerUtil.getLogger(LogType.CONTROLLER);
    private static final Logger LOGGER_ERROR = LoggerUtil.getLogger(LogType.ERROR);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @PostMapping("/enter")
    public ResponseEntity<User> enterUser(@RequestParam String login_email,
                                          @RequestParam String password_hash,
                                          @RequestParam String salt) {
        UserService userService = new UserService(userRepository);
        User user = userService.getByEmail(login_email);
        if (user == null) user = userService.getByLogin(login_email);
        if (user != null) {
            Password password = new PasswordService(passwordRepository).getPasswordByUser(user);
            if (password.getHash().equals(password_hash) && password.getSalt().equals(salt)) {
                LOGGER_CONTROLLER.info("Sign in is allowed for the user with id " + user.getId());
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        LOGGER_ERROR.error("Failed sign in try");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registrationUser(@RequestParam String first_name,
                                                   @RequestParam String last_name,
                                                   @RequestParam String login,
                                                   @RequestParam String email,
                                                   @RequestParam String phone_number,
                                                   @RequestParam String birthday,
                                                   @RequestParam String password_hash,
                                                   @RequestParam String salt) {
        UserService userService = new UserService(userRepository);
        try {
            User user = new User();
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            user.setLogin(login);
            user.setEmail(email);
            if (phone_number.equals("null")) phone_number = null;
            user.setPhone_number(phone_number);
            try { user.setBirthday(Date.valueOf(birthday)); }
            catch (IllegalArgumentException e) { user.setBirthday(null); }
            userService.addUser(user);
            Password password = new Password(password_hash, salt);
            password.setUser(user);
            new PasswordService(passwordRepository).addPassword(password);
            LOGGER_CONTROLLER.info("Sign up successful for the user with id " + user.getId());
            return new ResponseEntity<>("registration_success", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER_ERROR.error("Failed sign up try");
            return new ResponseEntity<>("registration_failed", HttpStatus.OK);
        }
    }

    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@RequestParam Long id,
                                           @RequestParam String first_name,
                                           @RequestParam String last_name,
                                           @RequestParam String login,
                                           @RequestParam String email,
                                           @RequestParam String phone_number,
                                           @RequestParam String birthday,
                                           @RequestParam String password_hash,
                                           @RequestParam String salt) {
        UserService userService = new UserService(userRepository);
        PasswordService passwordService = new PasswordService(passwordRepository);
        try {
            User user = userService.getById(id);
            if (user.getLogin() == null) throw new Exception();
            Password password = passwordService.getPasswordByUser(user);
            password.setHash(password_hash);
            password.setSalt(salt);
            passwordService.updatePassword(password);
            user.setFirst_name(first_name);
            user.setLast_name(last_name);
            user.setLogin(login);
            user.setEmail(email);
            if (phone_number.equals("null")) phone_number = null;
            user.setPhone_number(phone_number);
            try { user.setBirthday(Date.valueOf(birthday)); }
            catch (IllegalArgumentException e) { user.setBirthday(null); }
            user.setPassword(password);
            User outUser = userService.updateUser(user);
            LOGGER_CONTROLLER.info("Successful personal data update for the user with id " + id);
            return new ResponseEntity<>(outUser, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER_ERROR.error("Failed personal data update for the user with id " + id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        UserService userService = new UserService(userRepository);
        try {
            User user = userService.getById(id);
            if (user.getLogin() == null) throw new Exception();
            userService.deleteUser(user);
            LOGGER_CONTROLLER.info("Successful deleting the user with id " + id);
            return new ResponseEntity<>("delete_success", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER_ERROR.error("Failed deleting the user with id " + id);
            return new ResponseEntity<>("delete_failed", HttpStatus.OK);
        }
    }
}
