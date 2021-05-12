package blog.in.action.transcation.readonly;

import blog.in.action.transcation.entity.Orders;
import blog.in.action.transcation.repository.OrderRepository;
import blog.in.action.transcation.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class TransactionalReadOnlyTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void beforeEach() {
        orderRepository.deleteAll();
        List<Orders> ordersList = new ArrayList<>();
        for (int index = 0; index < 100; index++) {
            ordersList.add(new Orders(index + ""));
        }
        orderRepository.saveAll(ordersList);
    }

    @Test
    @DisplayName("READ ONLY TRUE")
    public void test_withReadOnlyTrue() {
        try {
            Orders order = new Orders("123");
            orderService.createOrderWithReadOnlyTrue(order);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            log.info("READ ONLY TRUE");
        }
    }

    @Test
    @DisplayName("FIND ALL READ ONLY TRUE")
    public void test_findAllWithReadOnlyTrue() {
        try {
            long start = System.currentTimeMillis();
            orderService.updateAllWithReadOnlyTrue();
            long end = System.currentTimeMillis();
            log.info((end - start) + " ms");
        } catch (Exception e) {
            log.warn(e.getMessage());
        } finally {
            log.info("FIND ALL READ ONLY TRUE");
        }
    }

    @Test
    @DisplayName("FIND ALL READ ONLY FALSE")
    public void test_findAllWithReadOnlyFalse() {
        try {
            long start = System.currentTimeMillis();
            orderService.updateAllWithReadOnlyFalse();
            long end = System.currentTimeMillis();
            log.info((end - start) + " ms");
        } catch (Exception e) {
            log.warn(e.getMessage());
        } finally {
            log.info("FIND ALL READ ONLY FALSE");
        }
    }
}
