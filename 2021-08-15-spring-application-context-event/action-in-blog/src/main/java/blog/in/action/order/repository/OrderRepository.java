package blog.in.action.order.repository;

import blog.in.action.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByDeliveryId(long deliveryId);
}
