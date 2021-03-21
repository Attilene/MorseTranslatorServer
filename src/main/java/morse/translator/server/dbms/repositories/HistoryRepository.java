package morse.translator.server.dbms.repositories;

import morse.translator.server.dbms.models.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Jpa Repository for creating CRUD queries for history table
 *
 * @see     History
 * @see     morse.translator.server.dbms.services.HistoryService
 * @author  Artem Bakanov aka Attilene
 */
public interface HistoryRepository extends JpaRepository<History, Long> {
    /**
     * Query for getting histories by userId
     *
     * @param   userId  user id that the history is linked to
     * @return          array of instances of History class that belong to user with userId
     */
    @Query("select his from History his where his.user.id = :user_id")
    List<History> findByUserId(@Param("user_id") Long userId);
}
