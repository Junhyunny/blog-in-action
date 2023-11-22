package action.in.blog.service;

import action.in.blog.domain.CardLikeEntity;
import action.in.blog.repository.CardLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultCardLikeService implements CardLikeService {

    private final CardLikeRepository cardLikeRepository;

    public DefaultCardLikeService(CardLikeRepository cardLikeRepository) {
        this.cardLikeRepository = cardLikeRepository;
    }

    @Transactional
    @Override
    public void increaseLikeCount(String cardId) {
        var cardLikeOptional = cardLikeRepository.findByIdWithLock(cardId);
        if (cardLikeOptional.isEmpty()) {
            cardLikeRepository.save(new CardLikeEntity(cardId));
            return;
        }
        var cardLike = cardLikeOptional.get();
        cardLike.increase();
    }
}
