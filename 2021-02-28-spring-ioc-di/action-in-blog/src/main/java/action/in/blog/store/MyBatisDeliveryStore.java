package action.in.blog.store;

import action.in.blog.domain.Delivery;

import java.util.Collections;
import java.util.List;

public class MyBatisDeliveryStore implements DeliveryStore {

    @Override
    public List<Delivery> getAllDeliveriesOrderByStartTime() {
        // some queries here
        return Collections.emptyList();
    }
}
