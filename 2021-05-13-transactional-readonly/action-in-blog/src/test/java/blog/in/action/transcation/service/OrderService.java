package blog.in.action.transcation.service;


import blog.in.action.transcation.entity.Orders;
import blog.in.action.transcation.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Orders createOrderWithReadOnlyTrue(Orders order) {
        return orderRepository.saveAndFlush(order);
    }

    @Transactional(readOnly = true)
    public void updateAllWithReadOnlyTrue() {
        List<Orders> orders = orderRepository.findAll();
        for (Orders order : orders) {
            order.setValue(order.getId());
        }
    }

    @Transactional
    public void updateAllWithReadOnlyFalse() {
        List<Orders> orders = orderRepository.findAll();
        for (Orders order : orders) {
            order.setValue(order.getId());
        }
    }
}