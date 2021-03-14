package morse.translator.server.test.services;

import morse.translator.server.dbms.models.History;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.services.HistoryService;
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
public class HistoryServiceTest {
    @Resource
    @Autowired
    private HistoryService historyService;

    @Resource
    @Autowired
    private UserService userService;

    @Test
    void addAndDelete() {
        User user = UserUtil.createUser();
        History history = HistoryUtil.createHistory();
        try {
            user.setHistories(new ArrayList<>() {{ add(history); }});
            userService.addUser(user);
            history.setUser(user);
            historyService.addHistory(history);
        } finally {
            historyService.deleteHistory(history);
            userService.deleteUser(user);
        }
    }

    @Test
    void update() {
        History history = HistoryUtil.createHistory();
        User user = UserUtil.createUser();
        try {
            user.setHistories(new ArrayList<>() {{ add(history); }});
            userService.addUser(user);
            history.setUser(user);
            historyService.addHistory(history);
            history.setStart_string("riestujfigmubhntbiuevmr7yebt7");
            historyService.updateHistory(history);
        } finally {
            historyService.deleteHistory(history);
            userService.deleteUser(user);
        }
    }

    @Test
    void getByUserId() {
        History history = HistoryUtil.createHistory();
        User user = UserUtil.createUser();
        try {
            user.setHistories(new ArrayList<>() {{ add(history); }});
            userService.addUser(user);
            historyService.findByUserId(user.getId());
        } finally {
            historyService.deleteHistory(history);
            userService.deleteUser(user);
        }
    }

    @Test
    void getAll() { historyService.getAllHistories(); }
}
