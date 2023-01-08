package blog.in.action.order.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long deliveryId;
    @Enumerated(value = EnumType.STRING)
    private OrderState orderState;

    public void finishDelivery() {
        orderState = OrderState.DELIVERY_FINISHED;
    }

    public void startDelivery(long deliveryId) {
        this.deliveryId= deliveryId;
        this.orderState = OrderState.DELIVERED;
    }
}
