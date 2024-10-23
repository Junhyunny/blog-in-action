package blog.in.action;

import blog.in.action.domain.Order;
import blog.in.action.domain.User;
import blog.in.action.repository.OrderRepository;
import blog.in.action.service.OrderService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DummyOrderRepository implements OrderRepository {

    @Override
    public UUID placeOrder(Order order) {
        return null;
    }

    @Override
    public Order findById(UUID id) {
        return null;
    }
}

public class DummyCaseTest {

    @Test
    void givenNotAdmin_whenPlaceOrder_thenThrowException() {
        OrderService sut = new OrderService(new DummyOrderRepository());


        assertThrows(RuntimeException.class, () -> {
            sut.placeOrder(
                    new User("junhyunny", "ROLE_USER"),
                    new Order()
            );
        });
    }
}