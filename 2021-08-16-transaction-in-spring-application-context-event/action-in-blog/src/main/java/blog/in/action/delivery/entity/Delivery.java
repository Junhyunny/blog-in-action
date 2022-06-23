package blog.in.action.delivery.entity;

import blog.in.action.order.entity.Order;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_DELIVERY")
public class Delivery {

    public Delivery(String deliveryCode, Order order) {
        this.deliveryCode = deliveryCode;
        this.order = order;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "DELIVERY_CODE")
    private String deliveryCode;

    @Column(name = "DELIVERY_END_TP", length = 1)
    private String deliveryEndTp;

    @OneToOne(targetEntity = Order.class)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;
}
