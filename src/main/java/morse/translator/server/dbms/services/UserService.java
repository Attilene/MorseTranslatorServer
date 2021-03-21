package morse.translator.server.dbms.services;

import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public User getById(Long id) { return userRepository.getOne(id); }

    public User getByEmail(String email) { return userRepository.findUserByEmail(email); }

    public User getByLogin(String login) { return userRepository.findUserByLogin(login); }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public User addUser(User user) { return userRepository.saveAndFlush(user); }

    public User updateUser(User user) { return userRepository.saveAndFlush(user); }

    public void deleteUser(User user) { userRepository.delete(user); }
}