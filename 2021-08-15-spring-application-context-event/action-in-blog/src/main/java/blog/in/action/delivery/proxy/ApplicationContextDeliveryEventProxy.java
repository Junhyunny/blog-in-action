package blog.in.action.delivery.proxy;

import blog.in.action.base.DeliveryCompleteEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextDeliveryEventProxy implements DeliveryEventProxy {

    private final ApplicationContext applicationContext;

    public ApplicationContextDeliveryEventProxy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void publishDeliveryCompleteEvent(DeliveryCompleteEvent deliveryCompleteEvent) {
        applicationContext.publishEvent(deliveryCompleteEvent);
    }
}
