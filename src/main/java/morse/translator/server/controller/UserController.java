package morse.translator.server.controller;

import morse.translator.server.exceptions.ResourceNotFoundException;
import morse.translator.server.model.User;
import morse.translator.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Page<User> getUsers(Pageable pageable) { return userRepository.findAll(pageable); }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) { return userRepository.save(user); }

    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable Long userId,
                           @Valid @RequestBody User userRequest) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setFirst_name(userRequest.getFirst_name());
                    user.setLast_name(userRequest.getLast_name());
                    user.setLogin(userRequest.getLogin());
                    user.setEmail(userRequest.getEmail());
                    user.setPhone_number(userRequest.getPhone_number());
                    user.setBirthday(userRequest.getBirthday());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }
}
