package blog.in.action.transcation;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@SpringBootTest
public class TransactionalTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @BeforeEach
    public void beforeEach() {
        orderRepository.deleteAll();
        deliveryRepository.deleteAll();
    }

    @Test
    @DisplayName("PARENT REQUIRED - CHILD REQUIRED")
    public void test_parentRequired_childRequired() {
        try {
            Orders order = new Orders("123");
            orderService.createOrderWithRequiredChildRequired(order);
        } catch (Exception e) {
            log.warn(e.getMessage());
        } finally {
            log.info("PARENT REQUIRED - CHILD REQUIRED END");
        }
    }

    @Test
    @DisplayName("PARENT X - CHILD REQUIRED")
    public void test_childRequired() {
        try {
            Orders order = new Orders("123");
            orderService.createOrderChildRequired(order);
        } catch (Exception e) {
            log.warn(e.getMessage());
        } finally {
            log.info("PARENT X - CHILD REQUIRED END");
        }
    }

    @Test
    @DisplayName("PARENT REQUIRED - CHILD SUPPORTS")
    public void test_parentRequired_childSupports() {
        try {
            Orders order = new Orders("123");
            orderService.createOrderWithRequiredChildSupports(order);
        } catch (Exception e) {
            log.warn(e.getMessage());
        } finally {
            log.info("PARENT REQUIRED - CHILD SUPPORTS END");
        }
    }

    @Test
    @DisplayName("PARENT X - CHILD SUPPORTS")
    public void test_childSupports() {
        try {
            Orders order = new Orders("123");
            orderService.createOrderChildSupports(order);
        } catch (Exception e) {
            log.warn(e.getMessage());
        } finally {
            log.info("PARENT X - CHILD SUPPORTS END");
        }
    }

    @Test
    @DisplayName("PARENT X - CHILD MANDATORY")
    public void test_childMandatory() {
        try {
            Orders order = new Orders("123");
            orderService.createOrderChildMandatory(order);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            log.info("PARENT X - CHILD MANDATORY END");
        }
    }

    @Test
    @DisplayName("PARENT REQUIRED - CHILD REQUIRES_NEW")
    public void test_parentRequired_childRequiresNew() {
        try {
            Orders order = new Orders("123");
            orderService.createOrderWithRequiredChildRequiresNew(order);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            log.info("PARENT REQUIRED - CHILD REQUIRES_NEW END");
        }
    }

    @Test
    @DisplayName("PARENT REQUIRED - CHILD NOT_SUPPORTED")
    public void test_parentRequired_childNotSupported() {
        try {
            Orders order = new Orders("123");
            orderService.createOrderWithRequiredChildNotSupported(order);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            log.info("PARENT REQUIRED - CHILD NOT_SUPPORTED END");
        }
    }

    @Test
    @DisplayName("PARENT REQUIRED - CHILD NEVER")
    public void test_parentRequired_childNever() {
        try {
            Orders order = new Orders("123");
            orderService.createOrderWithRequiredChildNever(order);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            log.info("PARENT REQUIRED - CHILD NEVER END");
        }
    }

    @Test
    @DisplayName("PARENT REQUIRED - CHILD NESTED")
    public void test_parentRequired_childNested() {
        try {
            Orders order = new Orders("123");
            orderService.createOrderWithRequiredChildNested(order);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            log.info("PARENT REQUIRED - CHILD NESTED END");
        }
    }
}

@Entity
@NoArgsConstructor
@AllArgsConstructor
class Delivery {

    @Id
    private String id;
}

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
class Orders {

    @Id
    private String id;

    public void setId(String id) {
        this.id = id;
    }
}

interface DeliveryRepository extends JpaRepository<Delivery, String> {

}

@Component
@RequiredArgsConstructor
class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private boolean isOk() {
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Delivery createDeliveryWithRequired(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Delivery createDeliveryWithSupports(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Delivery createDeliveryWithMandatory(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Delivery createDeliveryWithRequiresNew(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Delivery createDeliveryWithNotSupported(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.NEVER)
    public Delivery createDeliveryWithNever(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.NESTED)
    public Delivery createDeliveryWithNested(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }
}

interface OrderRepository extends JpaRepository<Orders, String> {

}

@Log4j2
@Component
@RequiredArgsConstructor
class OrderService {

    private final OrderRepository orderRepository;

    private final DeliveryService deliveryService;

    @Transactional(propagation = Propagation.REQUIRED)
    public Orders createOrderWithRequiredChildRequired(Orders order) {
        orderRepository.saveAndFlush(order);
        try {
            deliveryService.createDeliveryWithRequired(new Delivery(order.getId()));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return order;
    }

    public Orders createOrderChildRequired(Orders order) {
        orderRepository.saveAndFlush(order);
        deliveryService.createDeliveryWithRequired(new Delivery(order.getId()));
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Orders createOrderWithRequiredChildSupports(Orders order) {
        orderRepository.saveAndFlush(order);
        deliveryService.createDeliveryWithSupports(new Delivery(order.getId()));
        return order;
    }

    public Orders createOrderChildSupports(Orders order) {
        orderRepository.saveAndFlush(order);
        deliveryService.createDeliveryWithSupports(new Delivery(order.getId()));
        return order;
    }

    public Orders createOrderChildMandatory(Orders order) {
        orderRepository.saveAndFlush(order);
        deliveryService.createDeliveryWithMandatory(new Delivery(order.getId()));
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Orders createOrderWithRequiredChildRequiresNew(Orders order) {
        orderRepository.saveAndFlush(order);
        try {
            deliveryService.createDeliveryWithRequiresNew(new Delivery(order.getId()));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Orders createOrderWithRequiredChildNotSupported(Orders order) {
        orderRepository.saveAndFlush(order);
        deliveryService.createDeliveryWithNotSupported(new Delivery(order.getId()));
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Orders createOrderWithRequiredChildNever(Orders order) {
        orderRepository.saveAndFlush(order);
        deliveryService.createDeliveryWithNever(new Delivery(order.getId()));
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Orders createOrderWithRequiredChildNested(Orders order) {
        orderRepository.saveAndFlush(order);
        try {
            deliveryService.createDeliveryWithNested(new Delivery(order.getId()));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return order;
    }
}