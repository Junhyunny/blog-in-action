package blog.in.action.delivery.service;

import blog.in.action.common.event.OrderDeliveryCompleteEvent;
import blog.in.action.delivery.entity.Delivery;
import blog.in.action.delivery.repository.DeliveryRepository;
import java.util.Optional;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeliveryService {

    private final ApplicationContext applicationContext;

    private final DeliveryRepository deliveryRepository;

    public DeliveryService(ApplicationContext applicationContext, DeliveryRepository deliveryRepository) {
        this.applicationContext = applicationContext;
        this.deliveryRepository = deliveryRepository;
    }

    public void updateDeliveryComplete(String deliveryCode) {
        Optional<Delivery> optional = deliveryRepository.findByDeliveryCode(deliveryCode);
        if (optional.isEmpty()) {
            throw new RuntimeException(deliveryCode + " 코드에 해당하는 배송 정보가 없습니다.");
        }
        Delivery delivery = optional.get();
        delivery.setDeliveryEndTp("*");
        deliveryRepository.save(delivery);
        applicationContext.publishEvent(new OrderDeliveryCompleteEvent(delivery.getOrder().getId(), deliveryCode));
    }
}
