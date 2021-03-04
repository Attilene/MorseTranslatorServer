package morse.translator.server.controller;

import morse.translator.server.model.Password;
import morse.translator.server.model.User;
import morse.translator.server.repository.PasswordRepository;
import morse.translator.server.repository.UserRepository;
import morse.translator.server.service.PasswordService;
import morse.translator.server.service.UserService;
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
        User user;
        UserService userService = new UserService(userRepository);
        user = userService.getByEmail(login_email);
        if (user == null) user = userService.getByLogin(login_email);
        if (user != null) {
            Password password = new PasswordService(passwordRepository).getPasswordByUser(user);
            if (password.getHash().equals(password_hash) && password.getSalt().equals(salt)) return user;
        }
        return null;
    }
}
