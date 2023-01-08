package blog.in.action.delivery.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_DELIVERY")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long orderId;
    @Enumerated(value = EnumType.STRING)
    private DeliveryState deliveryState;

    public void finishDelivery() {
        deliveryState = DeliveryState.FINISH;
    }
}
