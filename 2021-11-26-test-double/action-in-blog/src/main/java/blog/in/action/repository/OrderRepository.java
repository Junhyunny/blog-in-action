package blog.in.action.repository;

import blog.in.action.domain.Order;

import java.util.UUID;

public interface OrderRepository {

    UUID placeOrder(Order order);

    Order findById(UUID id);
}
