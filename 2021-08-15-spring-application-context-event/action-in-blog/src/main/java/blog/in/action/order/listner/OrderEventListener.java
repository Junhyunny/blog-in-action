package blog.in.action.order.listner;

import blog.in.action.base.DeliveryCompleteEvent;
import blog.in.action.order.service.OrderService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final OrderService orderService;

    public OrderEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @EventListener
    public void listenOrderDeliveryCompleteEvent(DeliveryCompleteEvent deliveryCompleteEvent) {
        orderService.finishDelivery(deliveryCompleteEvent.getDeliveryId());
    }
}
