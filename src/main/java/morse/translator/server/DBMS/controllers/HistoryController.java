package morse.translator.server.DBMS.controllers;

import morse.translator.server.DBMS.exceptions.ResourceNotFoundException;
import morse.translator.server.DBMS.models.History;
import morse.translator.server.DBMS.repositories.HistoryRepository;
import morse.translator.server.DBMS.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class HistoryController {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/histories")
    public List<History> getHistory(@PathVariable Long userId) {
        return historyRepository.findByUserId(userId);
    }

    @PostMapping("/users/{userId}/histories")
    public History addHistory(@PathVariable Long userId,
                              @Valid @RequestBody History history) {
        return userRepository.findById(userId)
                .map(user -> {
                    history.setUser(user);
                    return historyRepository.save(history);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @PutMapping("/users/{userId}/histories/{historyId}")
    public History updateHistory(@PathVariable Long userId,
                                 @PathVariable Long historyId,
                                 @Valid @RequestBody History historyRequest){
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return historyRepository.findById(historyId)
                .map(history -> {
                    history.setStart_string(historyRequest.getStart_string());
                    history.setEnd_string(historyRequest.getEnd_string());
                    history.setOperation_time(historyRequest.getOperation_time());
                    return historyRepository.save(history);
                }).orElseThrow(() -> new ResourceNotFoundException("History not found with id " + historyId));
    }

    @DeleteMapping("/users/{userId}/histories/{historyId}")
    public ResponseEntity<?> deleteHistory(@PathVariable Long userId,
                                           @PathVariable Long historyId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return historyRepository.findById(historyId)
                .map(history -> {
                    historyRepository.delete(history);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("History not found with id " + historyId));
    }
}
