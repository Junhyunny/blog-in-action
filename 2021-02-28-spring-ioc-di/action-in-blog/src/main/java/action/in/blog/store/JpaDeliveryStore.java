package action.in.blog.store;

import action.in.blog.domain.Delivery;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class JpaDeliveryStore implements DeliveryStore {

    @Override
    public List<Delivery> getAllDeliveriesOrderByStartTime() {
        // some queries here
        return Collections.emptyList();
    }
}
