package blog.in.action;

import blog.in.action.domain.Order;
import blog.in.action.repository.OrderRepository;
import blog.in.action.service.OrderService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FakeOrderRepository implements OrderRepository {
    HashMap<UUID, Order> orders = new HashMap<>();

    @Override
    public UUID placeOrder(Order order) {
        return null;
    }

    @Override
    public Order findById(UUID id) {
        return orders.get(id);
    }
}

public class FakeCaseTest {

    @Test
    void givenOrderIsExisted_whenPlaceOrder_thenReturnOrder() {
        var orderId = UUID.fromString("a1b3360b-687e-4491-a6ca-f8f2d1474b6b");
        FakeOrderRepository fake = new FakeOrderRepository();
        fake.orders.put(
                orderId,
                new Order(orderId, 1000)
        );
        OrderService sut = new OrderService(fake);


        var result = sut.findById(orderId);


        assertEquals(
                new Order(orderId, 1000),
                result
        );
    }

    @Test
    void givenOrderIsNotExisted_whenPlaceOrder_thenThrowException() {
        var orderId = UUID.fromString("a1b3360b-687e-4491-a6ca-f8f2d1474b6b");
        FakeOrderRepository fake = new FakeOrderRepository();
        OrderService sut = new OrderService(fake);


        assertThrows(RuntimeException.class, () -> {
            sut.findById(orderId);
        });
    }
}