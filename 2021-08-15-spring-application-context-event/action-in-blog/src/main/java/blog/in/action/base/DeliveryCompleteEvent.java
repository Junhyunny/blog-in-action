package blog.in.action.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryCompleteEvent {


    private long deliveryId;

    public DeliveryCompleteEvent(long deliveryId) {
        this.deliveryId = deliveryId;
    }
}
