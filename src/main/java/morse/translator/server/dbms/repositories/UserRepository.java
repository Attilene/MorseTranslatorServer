package morse.translator.server.dbms.repositories;

import morse.translator.server.dbms.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Jpa Repository for creating CRUD queries for the users table
 *
 * @see     User
 * @author  Artem Bakanov aka Attilene
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Query for getting user by email
     *
     * @param   email  user email
     * @return         instance of User class that got by email
     */
    @Query("select us from User us where us.email = :email")
    User findUserByEmail(@Param("email") String email);

    /**
     * Query for getting user by login
     *
     * @param   login  user login
     * @return         instance of User class that got by login
     */
    @Query("select us from User us where us.login = :login")
    User findUserByLogin(@Param("login") String login);
}
