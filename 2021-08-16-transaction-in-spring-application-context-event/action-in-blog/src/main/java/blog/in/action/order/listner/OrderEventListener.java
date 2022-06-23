package blog.in.action.order.listner;

import blog.in.action.common.event.IntentionalExceptionEvent;
import blog.in.action.common.event.IntentionalExceptionInRequiresNewTransactionEvent;
import blog.in.action.order.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class OrderEventListener {

    private final OrderService orderService;

    public OrderEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @EventListener
    public void listenIntentionalExceptionEvent(IntentionalExceptionEvent event) {
        try {
            orderService.updateOrderDeliveryComplete(event.getOrderId(), event.getDeliveryCode());
        } catch (RuntimeException runtimeException) {
            log.warn(runtimeException.getMessage(), runtimeException);
        }
    }

    @EventListener
    public void listenIntentionalExceptionInRequiresNewEvent(IntentionalExceptionInRequiresNewTransactionEvent event) {
        try {
            orderService.updateOrderDeliveryCompleteInRequiresNewTransaction(event.getOrderId(), event.getDeliveryCode());
        } catch (RuntimeException runtimeException) {
            log.warn(runtimeException.getMessage(), runtimeException);
        }
    }
}
