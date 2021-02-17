package morse.translator.server.controller;

import morse.translator.server.exceptions.ResourceNotFoundException;
import morse.translator.server.model.Password;
import morse.translator.server.repository.PasswordRepository;
import morse.translator.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Deprecated
@RestController
public class PasswordController {
    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/passwords")
    public Password getPassword(@PathVariable Long userId) {
        return passwordRepository.findByUserId(userId);
    }

    @PostMapping("/users/{userId}/passwords")
    public Password addPassword(@PathVariable Long userId,
                                @Valid @RequestBody Password password) {
        return userRepository.findById(userId)
                .map(user -> {
                    password.setUser(user);
                    return passwordRepository.save(password);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @PutMapping("/users/{userId}/passwords/{passwordId}")
    public Password updatePassword(@PathVariable Long userId,
                                   @PathVariable Long passwordId,
                                   @Valid @RequestBody Password passwordRequest){
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return passwordRepository.findById(passwordId)
                .map(password -> {
                    password.setHash(passwordRequest.getHash());
                    password.setSalt(passwordRequest.getSalt());
                    return passwordRepository.save(password);
                }).orElseThrow(() -> new ResourceNotFoundException("Password not found with id " + passwordId));
    }

    @DeleteMapping("/users/{userId}/passwords/{passwordId}")
    public ResponseEntity<?> deletePassword(@PathVariable Long userId,
                                            @PathVariable Long passwordId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return passwordRepository.findById(passwordId)
                .map(password -> {
                    passwordRepository.delete(password);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Password not found with id " + passwordId));
    }
}
