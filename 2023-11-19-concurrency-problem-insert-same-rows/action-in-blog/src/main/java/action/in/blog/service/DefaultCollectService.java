package action.in.blog.service;

import action.in.blog.domain.CollectEntity;
import action.in.blog.exception.DuplicatedCollectException;
import action.in.blog.repository.CollectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultCollectService implements CollectService {

    private final CollectRepository collectRepository;

    public DefaultCollectService(CollectRepository collectRepository) {
        this.collectRepository = collectRepository;
    }

    @Transactional
    @Override
    public void collect(String userId, String cardId) {
        var exists = collectRepository.existsByUserIdAndCardId(userId, cardId);
        if (exists) {
            throw new DuplicatedCollectException();
        }
        try {
            collectRepository.save(new CollectEntity(userId, cardId));
        } catch (Exception e) {
            throw new DuplicatedCollectException();
        }
    }
}
