package morse.translator.server.service;

import morse.translator.server.model.History;
import morse.translator.server.model.User;
import morse.translator.server.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    public List<History> getAllHistories() { return historyRepository.findAll(); }

    public History getById(Long id) { return historyRepository.getOne(id); }

    public List<History> getHistoriesByUser(User user) { return historyRepository.findByUserId(user.getId()); }

    public History addHistory(History history) { return historyRepository.saveAndFlush(history); }

    public History updateHistory(History history) { return historyRepository.saveAndFlush(history); }

    public void deleteHistory(History history) { historyRepository.delete(history); }
}
