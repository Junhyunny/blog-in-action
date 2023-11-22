package action.in.blog.controller;

import action.in.blog.handler.CardLikeRetryHandler;
import action.in.blog.service.CardLikeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardLikeController {

    private final CardLikeService cardLikeService;
    private final CardLikeRetryHandler cardLikeRetryHandler;

    public CardLikeController(CardLikeService cardLikeService, CardLikeRetryHandler cardLikeRetryHandler) {
        this.cardLikeService = cardLikeService;
        this.cardLikeRetryHandler = cardLikeRetryHandler;
    }

    @PostMapping("/cards/{cardId}/likes")
    public void increase(@PathVariable String cardId) {
        try {
            cardLikeService.increaseLikeCount(cardId);
        } catch (Exception e) {
            cardLikeRetryHandler.retry(cardId);
        }
    }
}