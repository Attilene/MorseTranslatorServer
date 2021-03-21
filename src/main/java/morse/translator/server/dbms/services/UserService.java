package morse.translator.server.dbms.services;

import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for methods for manipulating with the users table
 *
 * @see     User
 * @see     UserRepository
 * @author  Artem Bakanov aka Attilene
 */
@Service
@Transactional
public class UserService {
    /**
     * Repository that contains queries for the users table
     */
    private final UserRepository userRepository;

    /**
     * Constructor for service
     *
     * @param  userRepository  repository that contains queries for the users table
     */
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    /**
     * Method for getting list of all instances of User class contains in the users table
     *
     * @return  all users that contains in the users table
     */
    public List<User> getAllUsers() { return userRepository.findAll(); }

    /**
     * Method for getting instance of User class by user id
     *
     * @param   id  user id
     * @return      user with user id
     */
    public User getById(Long id) { return userRepository.getOne(id); }

    /**
     * Method for getting instance of User class contains in the users table by email
     *
     * @param   email  user email
     * @return         user with email contained in the users table
     */
    public User getByEmail(String email) { return userRepository.findUserByEmail(email); }

    /**
     * Method for getting instance of User class contains in the users table by login
     *
     * @param   login  user login
     * @return         user with login contained in the users table
     */
    public User getByLogin(String login) { return userRepository.findUserByLogin(login); }

    /**
     * Method for adding new instance of User class to the users table
     *
     * @param   user  instance of User class for adding
     * @return        instance of User class that was added to the users table
     */
    public User addUser(User user) { return userRepository.saveAndFlush(user); }

    /**
     * Method for updating instance of User class in the users table
     *
     * @param   user  instance of User class for updating
     * @return        instance of User class that was updated in the users table
     */
    public User updateUser(User user) { return userRepository.saveAndFlush(user); }

    /**
     * Method for deleting instance of User class in the users table
     *
     * @param  user  instance of User class for deleting
     */
    public void deleteUser(User user) { userRepository.delete(user); }
}
