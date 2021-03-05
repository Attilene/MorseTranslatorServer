package morse.translator.server.controller;

import morse.translator.server.model.Password;
import morse.translator.server.model.User;
import morse.translator.server.repository.PasswordRepository;
import morse.translator.server.repository.UserRepository;
import morse.translator.server.service.PasswordService;
import morse.translator.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @PostMapping("/registration")
    public String registrationUser(@RequestParam String first_name,
                                   @RequestParam String last_name,
                                   @RequestParam String login,
                                   @RequestParam String email,
                                   @RequestParam String phone_number,
                                   @RequestParam String birthday,
                                   @RequestParam String password_hash,
                                   @RequestParam String salt) {
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
            UserService userService = new UserService(userRepository);
            userService.addUser(user);
            Password password = new Password(password_hash, salt);
            password.setUser(user);
            new PasswordService(passwordRepository).addPassword(password);
            return "registration_success";
        } catch (Exception e) { return "registration_failed"; }
    }

    @PutMapping("/update")
    public User updateUser(@RequestParam Long id,
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
            try {
                user.setBirthday(Date.valueOf(birthday));
            } catch (IllegalArgumentException e) {
                user.setBirthday(null);
            }
            user.setPassword(password);
            return userService.updateUser(user);
        } catch (Exception e) { return null; }
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        try {
            UserService userService = new UserService(userRepository);
            User user = userService.getById(id);
            if (user.getLogin() == null) throw new Exception();
            userService.deleteUser(user);
            return "delete_success";
        } catch (Exception e) { return "delete_failed"; }
    }
}
