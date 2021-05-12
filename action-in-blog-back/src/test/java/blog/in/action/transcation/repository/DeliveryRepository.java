package blog.in.action.transcation.repository;

import blog.in.action.transcation.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {

}