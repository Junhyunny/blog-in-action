package action.in.blog.join;

import lombok.Getter;

@Getter
public class TradeVO {

    private long id;
    private String providerId;
    private String providerName;
    private String consumerId;
    private String consumerName;
    private int basedPrice;
    private int contractSize;
}
