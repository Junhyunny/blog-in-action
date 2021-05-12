package blog.in.action.transcation.service;


import blog.in.action.transcation.entity.Delivery;
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