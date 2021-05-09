package blog.in.action.transcation;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionalTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void test_createOrder_returnOrder() {
        Order order = new Order();
        order.setId("123");
        assertThat(orderService.createOrder(order)).isEqualTo(order);
    }
}

class Item {

    private String id;
    private String name;
    private int unitPrice;
}

class Order {

    private String id;
    private String clientId;
    private String deliveryAddress;
    private List<Item> itemList;

    public void setId(String id) {
        this.id = id;
    }
}


@Component
class DeliveryService {

}

@Component
@RequiredArgsConstructor
class OrderService {

    private final DeliveryService deliveryService;

    @Transactional
    public Order createOrder(Order order) {
        return order;
    }
}