package morse.translator.server.test.services;

import morse.translator.server.dbms.models.Password;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.services.PasswordService;
import morse.translator.server.dbms.services.UserService;
import morse.translator.server.test.utils.PasswordUtil;
import morse.translator.server.test.utils.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@SpringBootTest
@EnableAutoConfiguration
@Configuration
public class PasswordServiceTest {
    @Resource
    @Autowired
    private PasswordService passwordService;

    @Resource
    @Autowired
    private UserService userService;

    @Test
    void addAndDelete() {
        User user = UserUtil.createUser();
        Password password = PasswordUtil.createPassword();
        try {
            user.setPassword(password);
            userService.addUser(user);
            password.setUser(user);
            passwordService.addPassword(password);
        } finally {
            passwordService.deletePassword(password);
            userService.deleteUser(user);
        }
    }

    @Test
    void update() {
        Password password = PasswordUtil.createPassword();
        User user = UserUtil.createUser();
        try {
            user.setPassword(password);
            userService.addUser(user);
            password.setUser(user);
            passwordService.addPassword(password);
            password.setHash("riestujfigmubhntbiuevmr7yebt7");
            passwordService.updatePassword(password);
        } finally {
            passwordService.deletePassword(password);
            userService.deleteUser(user);
        }
    }

    @Test
    void getByUserId() {
        Password password = PasswordUtil.createPassword();
        User user = UserUtil.createUser();
        try {
            user.setPassword(password);
            userService.addUser(user);
            passwordService.getPasswordByUser(user);
        } finally {
            passwordService.deletePassword(password);
            userService.deleteUser(user);
        }
    }

    @Test
    void getAll() { passwordService.getAllPasswords(); }
}
