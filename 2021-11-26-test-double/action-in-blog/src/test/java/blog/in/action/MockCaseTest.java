package blog.in.action;

import blog.in.action.domain.Order;
import blog.in.action.domain.User;
import blog.in.action.repository.OrderRepository;
import blog.in.action.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MockCaseTest {

    @Test
    void givenAdmin_whenPlaceOrder_thenReturnOderId() {
        OrderRepository mock = Mockito.mock(OrderRepository.class);
        Mockito.when(
                mock.placeOrder(any())
        ).thenReturn(
                UUID.fromString("a1b3360b-687e-4491-a6ca-f8f2d1474b6b")
        );
        OrderService sut = new OrderService(mock);


        var result = sut.placeOrder(
                new User("junhyunny", "ROLE_ADMIN"),
                new Order()
        );


        assertEquals(
                UUID.fromString("a1b3360b-687e-4491-a6ca-f8f2d1474b6b"),
                result
        );
    }

    @Test
    void givenAdmin_whenPlaceOrder_thenCallPlaceOrderOfRepository() {
        OrderRepository mock = Mockito.mock(OrderRepository.class);
        OrderService sut = new OrderService(mock);


        sut.placeOrder(
                new User("junhyunny", "ROLE_ADMIN"),
                new Order(1000)
        );


        verify(mock, times(1))
                .placeOrder(new Order(1000));
    }
}