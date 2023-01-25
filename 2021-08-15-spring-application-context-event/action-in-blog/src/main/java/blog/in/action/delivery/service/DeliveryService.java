package blog.in.action.delivery.service;

import blog.in.action.base.DeliveryCompleteEvent;
import blog.in.action.delivery.domain.Delivery;
import blog.in.action.delivery.proxy.ApplicationContextDeliveryEventProxy;
import blog.in.action.delivery.proxy.DeliveryEventProxy;
import blog.in.action.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DeliveryService {


    private final DeliveryRepository deliveryRepository;
    private final DeliveryEventProxy deliveryEventProxy;

    public DeliveryService(DeliveryRepository deliveryRepository, ApplicationContextDeliveryEventProxy deliveryEventProxy) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryEventProxy = deliveryEventProxy;
    }

    public void finishDelivery(long deliveryId) {
        Optional<Delivery> optional = deliveryRepository.findById(deliveryId);
        Delivery delivery = optional.orElseThrow(() -> new RuntimeException(String.format("[%s]에 해당하는 배송 정보가 없습니다.", deliveryId)));
        delivery.finishDelivery();
        deliveryEventProxy.publishDeliveryCompleteEvent(new DeliveryCompleteEvent(deliveryId));
    }
}
