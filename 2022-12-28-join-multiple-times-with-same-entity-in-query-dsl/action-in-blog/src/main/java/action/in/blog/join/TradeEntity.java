package action.in.blog.join;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
    private String providerId;
    private String consumerId;
    private int basedPrice;
    private int contractSize;
}
