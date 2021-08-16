package blog.in.action.delivery.service;

import blog.in.action.common.event.AsyncEvent;
import blog.in.action.delivery.entity.Delivery;
import blog.in.action.delivery.repository.DeliveryRepository;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
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
        log.info("비동기 이벤트 발행 전");
        applicationContext.publishEvent(new AsyncEvent(delivery.getOrder().getId(), deliveryCode));
        log.info("비동기 이벤트 발행 후");
    }
}
