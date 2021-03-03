package morse.translator.server.service;

import morse.translator.server.model.Password;
import morse.translator.server.model.User;
import morse.translator.server.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PasswordService {
    @Autowired
    private PasswordRepository passwordRepository;

    public List<Password> getAllPasswords() { return passwordRepository.findAll(); }

    public Password getById(Long id) { return passwordRepository.getOne(id); }

    public Password getPasswordByUser(User user) { return passwordRepository.findByUserId(user.getId()); }

    public Password addPassword(Password password) { return passwordRepository.saveAndFlush(password); }

    public Password updatePassword(Password password) { return passwordRepository.saveAndFlush(password); }

    public void deletePassword(Password password) { passwordRepository.delete(password); }
}
