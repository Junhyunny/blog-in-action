package action.in.blog.controller;

import action.in.blog.handler.CardLikeRetryHandler;
import action.in.blog.service.CardLikeService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class CardLikeControllerTest {

    @Test
    void retry_when_increase_like_throw_exception() throws Exception {

        var cardLikeService = mock(CardLikeService.class);
        var cardLikeRetryHandler = mock(CardLikeRetryHandler.class);
        var sut = MockMvcBuilders.standaloneSetup(
                new CardLikeController(cardLikeService, cardLikeRetryHandler)
        ).build();
        doThrow(new RuntimeException()).when(cardLikeService).increaseLikeCount("card-01");


        sut.perform(
                post("/api/cards/card-01/likes")
        );


        verify(cardLikeRetryHandler, times(1)).retry("card-01");
    }
}