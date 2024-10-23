package blog.in.action;

import blog.in.action.domain.Order;
import blog.in.action.domain.User;
import blog.in.action.repository.OrderRepository;
import blog.in.action.service.OrderService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpyOrderRepository implements OrderRepository {

    int placeOrderCalledTimes;
    Order argumentPlaceOrder;

    @Override
    public UUID placeOrder(Order order) {
        placeOrderCalledTimes++;
        argumentPlaceOrder = order;
        return UUID.randomUUID();
    }

    @Override
    public Order findById(UUID id) {
        return null;
    }
}

public class SpyCaseTest {

    @Test
    void givenAdmin_whenPlaceOrder_thenCallPlaceOrderOfRepository() {
        SpyOrderRepository spy = new SpyOrderRepository();
        OrderService sut = new OrderService(spy);


        sut.placeOrder(
                new User("junhyunny", "ROLE_ADMIN"),
                new Order(1000)
        );


        assertEquals(
                1,
                spy.placeOrderCalledTimes
        );
        assertEquals(
                new Order(1000),
                spy.argumentPlaceOrder
        );
    }
}