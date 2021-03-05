package morse.translator.server.repositories;

import morse.translator.server.models.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("select his from History his where his.user.id = :user_id")
    List<History> findByUserId(@Param("user_id") Long userId);
}
