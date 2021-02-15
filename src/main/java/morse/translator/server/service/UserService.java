package morse.translator.server.service;

import morse.translator.server.model.User;
import morse.translator.server.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public User addUser(User user) { return userRepository.saveAndFlush(user); }

    public User updateUser(User user) { return userRepository.saveAndFlush(user); }

    public void deleteUser(User user) { userRepository.delete(user); }
}
