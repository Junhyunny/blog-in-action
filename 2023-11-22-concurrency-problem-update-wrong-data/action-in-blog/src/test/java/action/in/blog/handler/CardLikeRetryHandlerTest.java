package action.in.blog.handler;

import action.in.blog.service.CardLikeService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CardLikeRetryHandlerTest {

    @Test
    void retry() {

        var cardLikeService = mock(CardLikeService.class);
        var sut = new CardLikeRetryHandler(cardLikeService);
        doThrow(new RuntimeException())
                .doNothing()
                .when(cardLikeService)
                .increaseLikeCount("card-01");


        sut.retry("card-01");
        sut.retry("card-02");
        var future = sut.retry("card-01");


        while(!future.isDone());
        verify(cardLikeService, times(1)).increaseLikeCount("card-02");
        verify(cardLikeService, times(3)).increaseLikeCount("card-01");
    }
}