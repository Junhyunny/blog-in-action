package blog.in.action.order.service;

import blog.in.action.order.domain.Order;
import blog.in.action.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void finishDelivery(long deliveryId) {
        Optional<Order> optional = orderRepository.findByDeliveryId(deliveryId);
        Order order = optional.orElseThrow(() -> new RuntimeException("[%s] 배송 아이디에 해당하는 주문 정보가 없습니다."));
        order.finishDelivery();
    }
}
