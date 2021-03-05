package morse.translator.server.controllers;

import morse.translator.server.models.Password;
import morse.translator.server.models.User;
import morse.translator.server.repositories.PasswordRepository;
import morse.translator.server.repositories.UserRepository;
import morse.translator.server.services.PasswordService;
import morse.translator.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EnterController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @PostMapping("/enter")
    public User enterUser(@RequestParam String login_email,
                          @RequestParam String password_hash,
                          @RequestParam String salt) {
        UserService userService = new UserService(userRepository);
        User user = userService.getByEmail(login_email);
        if (user == null) user = userService.getByLogin(login_email);
        if (user != null) {
            Password password = new PasswordService(passwordRepository).getPasswordByUser(user);
            if (password.getHash().equals(password_hash) && password.getSalt().equals(salt)) return user;
        }
        return null;
    }
}
