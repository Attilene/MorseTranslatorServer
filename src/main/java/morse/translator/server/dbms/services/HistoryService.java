package morse.translator.server.dbms.services;

import morse.translator.server.dbms.models.History;
import morse.translator.server.dbms.repositories.HistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for methods for manipulating with the histories table
 *
 * @see     History
 * @see     HistoryRepository
 * @author  Artem Bakanov aka Attilene
 */
@Service
@Transactional
public class HistoryService {
    /**
     * Repository that contains queries for the histories table
     */
    private final HistoryRepository historyRepository;

    /**
     * Constructor for service
     *
     * @param  historyRepository  repository that contains queries for the histories table
     */
    public HistoryService(HistoryRepository historyRepository) { this.historyRepository = historyRepository; }

    /**
     * Method for getting list of all instances of History class contains in the histories table
     *
     * @return  all histories that contains in the histories table
     */
    public List<History> getAllHistories() { return historyRepository.findAll(); }

    /**
     * Method for getting instance of History class by history id
     *
     * @param   id  history id
     * @return      history with history id
     */
    public History getById(Long id) { return historyRepository.getOne(id); }

    /**
     * Method for getting list of instances of History class contains in the histories table by user id
     *
     * @param   user_id user id
     * @return          histories with user id contained in the histories table
     */
    public List<History> findByUserId(Long user_id) { return historyRepository.findByUserId(user_id); }

    /**
     * Method for adding new instance of History class to the histories table
     *
     * @param   history  instance of History class for adding
     * @return           instance of History class that was added to the histories table
     */
    public History addHistory(History history) { return historyRepository.saveAndFlush(history); }

    /**
     * Method for updating instance of History class in the histories table
     *
     * @param   history  instance of History class for updating
     * @return           instance of History class that was updated in the histories table
     */
    public History updateHistory(History history) { return historyRepository.saveAndFlush(history); }

    /**
     * Method for deleting instance of History class in the histories table
     *
     * @param  history  instance of History class for deleting
     */
    public void deleteHistory(History history) { historyRepository.delete(history); }
}
