package morse.translator.server.repositories;

import morse.translator.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select us from User us where us.email = :email")
    User findUserByEmail(@Param("email") String email);

    @Query("select us from User us where us.login = :login")
    User findUserByLogin(@Param("login") String login);
}
