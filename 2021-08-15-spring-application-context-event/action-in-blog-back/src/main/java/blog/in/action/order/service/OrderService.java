package blog.in.action.order.service;

import blog.in.action.order.entity.Order;
import blog.in.action.order.repository.OrderRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void updateOrderDeliveryComplete(long orderId, String deliveryCode) {
        Optional<Order> optional = orderRepository.findById(orderId);
        if (optional.isEmpty()) {
            throw new RuntimeException(deliveryCode + " 배송 코드에 해당하는 주문 정보가 없습니다.");
        }
        Order order = optional.get();
        order.setOrderState("DELIVERY_COMPLETE");
        orderRepository.save(order);
    }
}
