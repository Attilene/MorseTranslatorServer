package morse.translator.server.DBMS.repositories;

import morse.translator.server.DBMS.models.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordRepository extends JpaRepository<Password, Long> {
    List<Password> findByUserId(Long userId);
}
