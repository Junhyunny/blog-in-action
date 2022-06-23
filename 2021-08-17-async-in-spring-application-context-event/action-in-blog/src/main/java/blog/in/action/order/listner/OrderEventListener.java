package blog.in.action.order.listner;

import blog.in.action.common.event.AsyncEvent;
import blog.in.action.order.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class OrderEventListener {

    private final OrderService orderService;

    public OrderEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @Async
    @EventListener
    public void listenAsyncEvent(AsyncEvent event) {
        log.info("비동기 이벤트 수신");
        orderService.updateOrderDeliveryComplete(event.getOrderId(), event.getDeliveryCode());
    }
}
