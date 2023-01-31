package action.in.blog.store;

import action.in.blog.domain.Delivery;

import java.util.List;

public interface DeliveryStore {
    List<Delivery> getAllDeliveriesOrderByStartTime();
}
