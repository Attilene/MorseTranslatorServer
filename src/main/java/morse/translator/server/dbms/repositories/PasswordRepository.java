package morse.translator.server.dbms.repositories;

import morse.translator.server.dbms.models.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Jpa Repository for creating CRUD queries for the passwords table
 *
 * @see     Password
 * @author  Artem Bakanov aka Attilene
 */
public interface PasswordRepository extends JpaRepository<Password, Long> {
    /**
     * Query for getting user password by userId
     *
     * @param   userId  user id that the password is linked to
     * @return          instance of Password class that belong to user with userId
     */
    @Query("select pass from Password pass where pass.user.id = :user_id")
    Password findByUserId(@Param("user_id") Long userId);
}
