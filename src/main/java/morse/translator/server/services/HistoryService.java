package morse.translator.server.services;

import morse.translator.server.models.History;
import morse.translator.server.models.User;
import morse.translator.server.repositories.HistoryRepository;
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

    public List<History> getHistoriesByUser(User user) { return historyRepository.findByUserId(user.getId()); }

    public History addHistory(History history) { return historyRepository.saveAndFlush(history); }

    public History updateHistory(History history) { return historyRepository.saveAndFlush(history); }

    public void deleteHistory(History history) { historyRepository.delete(history); }
}
