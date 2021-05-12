package blog.in.action.transcation.propagation;

import blog.in.action.transcation.entity.Orders;
import blog.in.action.transcation.repository.DeliveryRepository;
import blog.in.action.transcation.repository.OrderRepository;
import blog.in.action.transcation.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

