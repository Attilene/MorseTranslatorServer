package morse.translator.server.DBMS.repositories;

import morse.translator.server.DBMS.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
