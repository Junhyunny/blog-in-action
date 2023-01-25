package blog.in.action.delivery.service;

import blog.in.action.delivery.domain.Delivery;
import blog.in.action.delivery.domain.DeliveryState;
import blog.in.action.delivery.repository.DeliveryRepository;
import blog.in.action.order.domain.Order;
import blog.in.action.order.domain.OrderState;
import blog.in.action.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class DeliveryServiceTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryService sut;

    @Test
    @Transactional
    public void delivery_is_finished_then_order_state_is_changed() {
        Order order = Order.builder()
                .build();
        orderRepository.save(order);
        Delivery delivery = Delivery.builder()
                .orderId(order.getId())
                .deliveryState(DeliveryState.START)
                .build();
        deliveryRepository.save(delivery);
        order.startDelivery(delivery.getId());
        orderRepository.flush();


        sut.finishDelivery(delivery.getId());


        Optional<Order> optional = orderRepository.findByDeliveryId(delivery.getId());
        Order result = optional.get();
        assertThat(result.getOrderState(), equalTo(OrderState.DELIVERY_FINISHED));
    }
}
