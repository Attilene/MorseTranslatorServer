package morse.translator.server.controllers;

import morse.translator.server.components.Translator;
import morse.translator.server.dbms.models.History;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.HistoryRepository;
import morse.translator.server.dbms.repositories.UserRepository;
import morse.translator.server.dbms.services.HistoryService;
import morse.translator.server.dbms.services.UserService;
import morse.translator.server.logger.LogType;
import morse.translator.server.logger.LoggerUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
public class HistoryController {
    private static final Logger LOGGER_CONTROLLER = LoggerUtil.getLogger(LogType.CONTROLLER);
    private static final Logger LOGGER_ERROR = LoggerUtil.getLogger(LogType.ERROR);

    @Autowired
    UserRepository userRepository;

    @Autowired
    HistoryRepository historyRepository;

    @PostMapping("/histories")
    public ResponseEntity<List<History>> getHistories(@RequestParam Long user_id) {
        HistoryService historyService = new HistoryService(historyRepository);
        try {
            List<History> histories = historyService.findByUserId(user_id);
            LOGGER_CONTROLLER.info("Histories are received for user with id " + user_id);
            return new ResponseEntity<>(histories, HttpStatus.OK);
        }
        catch (Exception e) {
            LOGGER_ERROR.error("There is no histories for user with id " + user_id);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/history")
    public ResponseEntity<History> addHistory(@RequestParam String start_string,
                              @RequestParam Long user_id,
                              @RequestParam Boolean morse,
                              @RequestParam Boolean language) {
        UserService userService = new UserService(userRepository);
        HistoryService historyService = new HistoryService(historyRepository);
        Translator translator = new Translator();
        try {
            translator.setStart_str(start_string);
            translator.translate(morse, language);
            History history = new History(translator.getStart_str(), translator.getEnd_str(), Date.from(Instant.now()));
            User user = userService.getById(user_id);
            if (user.getLogin() == null) throw new Exception();
            history.setUser(user);
            History outHistory = historyService.addHistory(history);
            LOGGER_CONTROLLER.info("Added a history for user with id " + user_id);
            return new ResponseEntity<>(outHistory, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER_ERROR.error("Failed to add the history for user with id " + user_id);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/history")
    public ResponseEntity<String> deleteHistory(@RequestParam Long id) {
        try {
            HistoryService historyService = new HistoryService(historyRepository);
            History history = historyService.getById(id);
            if (history.getStart_string() == null) throw new Exception();
            historyService.deleteHistory(history);
            LOGGER_CONTROLLER.info("Successful deleting the history with id " + id);
            return new ResponseEntity<>("history_delete_success", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER_ERROR.error("Failed to delete the history with id " + id);
            return new ResponseEntity<>("history_delete_failed", HttpStatus.BAD_REQUEST);
        }
    }
}
