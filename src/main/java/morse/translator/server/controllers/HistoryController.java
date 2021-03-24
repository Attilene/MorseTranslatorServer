package morse.translator.server.controllers;

import morse.translator.server.components.translator.Translator;
import morse.translator.server.dbms.models.History;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.HistoryRepository;
import morse.translator.server.dbms.repositories.UserRepository;
import morse.translator.server.dbms.services.HistoryService;
import morse.translator.server.dbms.services.UserService;
import morse.translator.server.utils.logger.LogType;
import morse.translator.server.utils.logger.LoggerUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * <p>Controller for processing requests translation history`s manipulating commands</p>
 * Supported operations: get histories by user, add history to user, delete history
 *
 * @author  Artem Bakanov aka Attilene
 */
@RestController
public class HistoryController {
    private static final Logger LOGGER_CONTROLLER = LoggerUtil.getLogger(LogType.CONTROLLER);
    private static final Logger LOGGER_ERROR = LoggerUtil.getLogger(LogType.ERROR);

    /**
     * Repository for manipulating data in the users table
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Repository for manipulating data in the histories table
     */
    @Autowired
    HistoryRepository historyRepository;

    /**
     * Method for processing post-requests of getting user`s histories
     * <p>API: POST:/histories</p>
     *
     * @param   user_id  user id
     * @return           list of instances of History model class or null, if user by user_id does not exist
     */
    @PostMapping("/histories")
    public ResponseEntity<List<History>> getHistories(@RequestParam Long user_id) {
        HistoryService historyService = new HistoryService(historyRepository);
        try {
            List<History> histories = historyService.findByUserId(user_id);
            LOGGER_CONTROLLER.info("Histories are received for user with id " + user_id);
            return new ResponseEntity<>(histories, HttpStatus.OK);
        }
        catch (Exception e) {
            LOGGER_ERROR.error("There is no histories for user with id " + user_id, e);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    /**
     * Method for processing post-requests of adding new history to user entity
     * <p>API: POST:/history</p>
     *
     * @param   start_string  history start string
     * @param   user_id       user id
     * @param   morse         variable for translating to Morse code or back
     * @param   language      variable for chosen natural language
     * @return                instance of History model class or null, if adding history to user is failed
     */
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
            LOGGER_ERROR.error("Failed to add the history for user with id " + user_id, e);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    /**
     * Method for processing delete-requests of deleting history from the histories table
     * <p>API: DELETE:/history</p>
     *
     * @param   id  history id
     * @return      "history_delete_success" or "history_delete_failed", if deleting the history is failed
     */
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
            LOGGER_ERROR.error("Failed to delete the history with id " + id, e);
            return new ResponseEntity<>("history_delete_failed", HttpStatus.OK);
        }
    }
}
