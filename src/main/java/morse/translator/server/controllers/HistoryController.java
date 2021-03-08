package morse.translator.server.controllers;

import morse.translator.server.components.Translator;
import morse.translator.server.dbms.models.History;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.HistoryRepository;
import morse.translator.server.dbms.repositories.UserRepository;
import morse.translator.server.dbms.services.HistoryService;
import morse.translator.server.dbms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

@RestController
public class HistoryController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    HistoryRepository historyRepository;

    @PostMapping("/history")
    public History addHistory(@RequestParam String start_string,
                              @RequestParam Long user_id,
                              @RequestParam Boolean morse,
                              @RequestParam Boolean language) {
        UserService userService = new UserService(userRepository);
        HistoryService historyService = new HistoryService(historyRepository);
        Translator translator = new Translator();
        try {
            translator.setStart_str(start_string);
            translator.translate(morse, language);
            String end_string = translator.getEnd_str();
            History history = new History(start_string, end_string, Date.from(Instant.now()));
            User user = userService.getById(user_id);
            if (user.getLogin() == null) throw new Exception();
            history.setUser(user);
            return historyService.addHistory(history);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/history")
    public String deleteHistory(@RequestParam Long id) {
        try {
            HistoryService historyService = new HistoryService(historyRepository);
            History history = historyService.getById(id);
            if (history.getStart_string() == null) throw new Exception();
            historyService.deleteHistory(history);
            return "history_delete_success";
        } catch (Exception e) { return "history_delete_failed"; }
    }
}
