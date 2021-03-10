package morse.translator.server.dbms.services;

import morse.translator.server.dbms.models.History;
import morse.translator.server.dbms.models.User;
import morse.translator.server.dbms.repositories.HistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) { this.historyRepository = historyRepository; }

    public List<History> getAllHistories() { return historyRepository.findAll(); }

    public History getById(Long id) { return historyRepository.getOne(id); }

    public List<History> findByUserId(Long userId) { return historyRepository.findByUserId(userId); }

    public History addHistory(History history) { return historyRepository.saveAndFlush(history); }

    public History updateHistory(History history) { return historyRepository.saveAndFlush(history); }

    public void deleteHistory(History history) { historyRepository.delete(history); }
}
