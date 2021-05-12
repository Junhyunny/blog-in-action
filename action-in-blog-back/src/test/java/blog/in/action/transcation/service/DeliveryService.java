package blog.in.action.transcation.service;

import blog.in.action.transcation.entity.Delivery;
import blog.in.action.transcation.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private boolean isOk() {
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Delivery createDeliveryWithRequired(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Delivery createDeliveryWithSupports(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Delivery createDeliveryWithMandatory(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Delivery createDeliveryWithRequiresNew(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Delivery createDeliveryWithNotSupported(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.NEVER)
    public Delivery createDeliveryWithNever(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }

    @Transactional(propagation = Propagation.NESTED)
    public Delivery createDeliveryWithNested(Delivery delivery) {
        deliveryRepository.saveAndFlush(delivery);
        if (!isOk()) {
            throw new RuntimeException();
        }
        return delivery;
    }
}