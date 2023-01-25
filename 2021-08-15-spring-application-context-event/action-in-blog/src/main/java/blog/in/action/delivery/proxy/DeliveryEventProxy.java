package blog.in.action.delivery.proxy;

import blog.in.action.base.DeliveryCompleteEvent;

public interface DeliveryEventProxy {

    void publishDeliveryCompleteEvent(DeliveryCompleteEvent deliveryCompleteEvent);
}
