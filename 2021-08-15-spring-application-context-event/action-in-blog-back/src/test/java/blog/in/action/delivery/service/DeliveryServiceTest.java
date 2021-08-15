package blog.in.action.delivery.service;

import static org.assertj.core.api.Assertions.assertThat;
import blog.in.action.delivery.entity.Delivery;
import blog.in.action.delivery.repository.DeliveryRepository;
import blog.in.action.order.entity.Order;
import blog.in.action.order.repository.OrderRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeliveryServiceTest {

    private static String DELIVERY_CODE = "DELIVERY_CODE";

    private static String ORDER_CODE = "ORDER_CODE";

    private static String DELIVERY_COMPLETE = "DELIVERY_COMPLETE";

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryService deliveryService;

    @BeforeEach
    public void beforeEach() {
        deliveryRepository.deleteAll();
        orderRepository.deleteAll();
        Order order = new Order(ORDER_CODE);
        orderRepository.save(order);
        Delivery delivery = new Delivery(DELIVERY_CODE, order);
        deliveryRepository.save(delivery);
    }

    @Test
    public void test_updateDeliveryComplete_changeOrderState() {
        deliveryService.updateDeliveryComplete(DELIVERY_CODE);
        Optional<Order> optional = orderRepository.findByOrderCode(ORDER_CODE);
        assertThat(optional).isNotEmpty();
        assertThat(optional.get().getOrderState()).isEqualTo(DELIVERY_COMPLETE);
    }
}
