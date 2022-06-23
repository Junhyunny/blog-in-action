package blog.in.action.common.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AsyncEvent {

    private long orderId;

    private String deliveryCode;

    public AsyncEvent(long orderId, String deliveryCode) {
        this.orderId = orderId;
        this.deliveryCode = deliveryCode;
    }
}
