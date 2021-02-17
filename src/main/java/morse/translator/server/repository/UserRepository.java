package morse.translator.server.repository;

import morse.translator.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    User findUserByLogin(String login);
}
