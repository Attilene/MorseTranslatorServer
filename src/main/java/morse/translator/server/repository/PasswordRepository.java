package morse.translator.server.repository;

import morse.translator.server.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PasswordRepository extends JpaRepository<Password, Long> {
    @Query("select pass from Password pass where pass.user.id = :user_id")
    Password findByUserId(@Param("user_id") Long userId);
}
