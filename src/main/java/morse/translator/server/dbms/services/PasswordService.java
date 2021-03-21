package morse.translator.server.dbms.services;

import morse.translator.server.dbms.models.Password;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.PasswordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for methods for manipulating with the passwords table
 *
 * @see     Password
 * @see     PasswordRepository
 * @author  Artem Bakanov aka Attilene
 */
@Service
@Transactional
public class PasswordService {
    /**
     * Repository that contains queries for the passwords table
     */
    private final PasswordRepository passwordRepository;

    /**
     * Constructor for service
     *
     * @param  passwordRepository  repository that contains queries for the passwords table
     */
    public PasswordService(PasswordRepository passwordRepository) { this.passwordRepository = passwordRepository; }

    /**
     * Method for getting list of all instances of Password class contains in the passwords table
     *
     * @return  all passwords that contains in the passwords table
     */
    public List<Password> getAllPasswords() { return passwordRepository.findAll(); }

    /**
     * Method for getting instance of Password class by password id
     *
     * @param   id  password id
     * @return      password with password id
     */
    public Password getById(Long id) { return passwordRepository.getOne(id); }

    /**
     * Method for getting instance of Password class contains in the passwords table by user
     *
     * @param   user  instance of User class
     * @return        password with user contained in the passwords table
     */
    public Password getPasswordByUser(User user) { return passwordRepository.findByUserId(user.getId()); }

    /**
     * Method for adding new instance of Password class to the passwords table
     *
     * @param   password  instance of Password class for adding
     * @return            instance of Password class that was added to the passwords table
     */
    public Password addPassword(Password password) { return passwordRepository.saveAndFlush(password); }

    /**
     * Method for updating instance of Password class in the passwords table
     *
     * @param   password  instance of Password class for updating
     * @return            instance of Password class that was updated in the passwords table
     */
    public Password updatePassword(Password password) { return passwordRepository.saveAndFlush(password); }

    /**
     * Method for deleting instance of Password class in the passwords table
     *
     * @param  password  instance of Password class for deleting
     */
    public void deletePassword(Password password) { passwordRepository.delete(password); }
}
