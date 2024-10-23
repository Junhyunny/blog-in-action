package blog.in.action;


import blog.in.action.domain.Order;
import blog.in.action.domain.User;
import blog.in.action.repository.OrderRepository;
import blog.in.action.service.OrderService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StubOrderRepository implements OrderRepository {

    UUID returnOrderId;

    public void setReturnOrderId(UUID returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    @Override
    public UUID placeOrder(Order order) {
        return returnOrderId;
    }

    @Override
    public Order findById(UUID id) {
        return null;
    }
}

public class StubCaseTest {

    @Test
    void givenAdmin_whenPlaceOrder_thenReturnOderId() {
        StubOrderRepository stub = new StubOrderRepository();
        stub.setReturnOrderId(UUID.fromString("a1b3360b-687e-4491-a6ca-f8f2d1474b6b"));
        OrderService sut = new OrderService(stub);


        var result = sut.placeOrder(
                new User("junhyunny", "ROLE_ADMIN"),
                new Order()
        );


        assertEquals(
                UUID.fromString("a1b3360b-687e-4491-a6ca-f8f2d1474b6b"),
                result
        );
    }
}