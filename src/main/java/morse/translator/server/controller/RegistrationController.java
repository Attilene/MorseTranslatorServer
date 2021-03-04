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
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @PostMapping("/registration")
    public String registrationUser(@RequestParam String first_name,
                                   @RequestParam String last_name,
                                   @RequestParam String login,
                                   @RequestParam String email,
                                   @RequestParam(required = false) String phone_number,
                                   @RequestParam(required = false) Date birthday,
                                   @RequestParam String password_hash,
                                   @RequestParam String salt) {
        User user = new User(first_name, last_name, login, email, phone_number, birthday);
        Password password = new Password(password_hash, salt);
        new UserService(userRepository).addUser(user);
        password.setUser(user);
        new PasswordService(passwordRepository).addPassword(password);

        return "registration_success";
    }
}
