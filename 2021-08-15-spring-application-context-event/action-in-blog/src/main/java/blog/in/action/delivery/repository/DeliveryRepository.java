package blog.in.action.delivery.repository;

import blog.in.action.delivery.domain.Delivery;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
