package morse.translator.server.dbms.services;

import morse.translator.server.dbms.models.Password;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.PasswordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PasswordService {
    private final PasswordRepository passwordRepository;

    public PasswordService(PasswordRepository passwordRepository) { this.passwordRepository = passwordRepository; }

    public List<Password> getAllPasswords() { return passwordRepository.findAll(); }

    public Password getById(Long id) { return passwordRepository.getOne(id); }

    public Password getPasswordByUser(User user) { return passwordRepository.findByUserId(user.getId()); }

    public Password addPassword(Password password) { return passwordRepository.saveAndFlush(password); }

    public Password updatePassword(Password password) { return passwordRepository.saveAndFlush(password); }

    public void deletePassword(Password password) { passwordRepository.delete(password); }
}