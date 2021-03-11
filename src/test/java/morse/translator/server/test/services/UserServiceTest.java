package morse.translator.server.test.services;

import morse.translator.server.dbms.models.History;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.services.UserService;
import morse.translator.server.test.utils.HistoryUtil;
import morse.translator.server.test.utils.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;

@SpringBootTest
@EnableAutoConfiguration
@Configuration
public class UserServiceTest {
    @Resource
    @Autowired
    private UserService userService;

    @Test
    void addAndDelete() {
        User user = UserUtil.createUser();
        try {
            userService.addUser(user);
        } finally {
            userService.deleteUser(user);
        }
    }

    @Test
    void update() {
        History history = HistoryUtil.createHistory();
        User user = UserUtil.createUser();
        try {
            userService.addUser(user);
            user.setFirst_name("Дмитрий");
            user.setHistories(new ArrayList<>() {{ add(history); }});
            userService.updateUser(user);
        } finally {
            userService.deleteUser(user);
        }
    }

    @Test
    void getByEmail() {
        User user = UserUtil.createUser();
        try {
            userService.addUser(user);
            userService.getByEmail("awt@mail.ru");
        } finally {
            userService.deleteUser(user);
        }
    }

    @Test
    void getByLogin() {
        User user = UserUtil.createUser();
        try {
            userService.addUser(user);
            userService.getByLogin("qwerty");
        } finally {
            userService.deleteUser(user);
        }
    }

    @Test
    void getAll() { userService.getAllUsers(); }
}
