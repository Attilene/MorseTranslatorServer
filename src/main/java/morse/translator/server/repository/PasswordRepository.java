package morse.translator.server.repository;

import morse.translator.server.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordRepository extends JpaRepository<Password, Long> {
    Password findByUserId(Long userId);
}
