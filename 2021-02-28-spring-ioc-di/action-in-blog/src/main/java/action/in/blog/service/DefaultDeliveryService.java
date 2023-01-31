package action.in.blog.service;

import action.in.blog.domain.Delivery;
import action.in.blog.store.DeliveryStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultDeliveryService {

    private final DeliveryStore deliveryStore;

    public DefaultDeliveryService(DeliveryStore deliveryStore) {
        this.deliveryStore = deliveryStore;
    }

    public List<Delivery> getAllDeliveriesOrderByStartTime() {
        return deliveryStore.getAllDeliveriesOrderByStartTime();
    }
}
