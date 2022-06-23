package blog.in.action.transcation.repository;

import blog.in.action.transcation.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, String> {

}
