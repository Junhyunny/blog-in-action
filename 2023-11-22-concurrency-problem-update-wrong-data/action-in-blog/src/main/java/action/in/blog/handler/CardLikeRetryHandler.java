package action.in.blog.handler;

import action.in.blog.service.CardLikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class CardLikeRetryHandler {

    private final Logger logger = LoggerFactory.getLogger(CardLikeRetryHandler.class);
    private final CardLikeService cardLikeService;
    private final Queue<String> retryQueue;
    private final ExecutorService retryHandler;

    public CardLikeRetryHandler(CardLikeService cardLikeService) {
        this.cardLikeService = cardLikeService;
        this.retryQueue = new ConcurrentLinkedDeque<>();
        this.retryHandler = Executors.newFixedThreadPool(1);
    }

    private void handle() {
        while (!retryQueue.isEmpty()) {
            logger.warn("queue size - {}", retryQueue.size());
            var id = retryQueue.poll();
            try {
                cardLikeService.increaseLikeCount(id);
            } catch (Exception e) {
                logger.warn("exception cause - {}", e.getMessage());
                retryQueue.add(id);
            }
        }
    }

    public Future<?> retry(String cardId) {
        retryQueue.add(cardId);
        return retryHandler.submit(this::handle);
    }
}
