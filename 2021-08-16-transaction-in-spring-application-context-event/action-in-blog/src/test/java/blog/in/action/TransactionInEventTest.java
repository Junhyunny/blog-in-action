package blog.in.action;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import blog.in.action.delivery.entity.Delivery;
import blog.in.action.delivery.repository.DeliveryRepository;
import blog.in.action.delivery.service.DeliveryService;
import blog.in.action.order.entity.Order;
import blog.in.action.order.repository.OrderRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

@SpringBootTest
public class TransactionInEventTest {

    private static String DELIVERY_CODE = "DELIVERY_CODE";

    private static String ORDER_CODE = "ORDER_CODE";

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
    public void test_updateDeliveryComplete_rollbackDeliveryEndTp() {
        assertThrows(UnexpectedRollbackException.class, () -> deliveryService.updateDeliveryComplete(DELIVERY_CODE));
        Optional<Delivery> deliveryOptional = deliveryRepository.findByDeliveryCode(DELIVERY_CODE);
        assertThat(deliveryOptional).isNotEmpty();
        assertThat(deliveryOptional.get().getDeliveryEndTp()).isNull();
    }

    @Test
    public void test_updateDeliveryComplete_doNotRollbackDeliveryEndTp() {
        deliveryService.updateDeliveryCompleteInRequiresNewTransaction(DELIVERY_CODE);
        Optional<Delivery> deliveryOptional = deliveryRepository.findByDeliveryCode(DELIVERY_CODE);
        assertThat(deliveryOptional).isNotEmpty();
        assertThat(deliveryOptional.get().getDeliveryEndTp()).isEqualTo("*");
    }
}
