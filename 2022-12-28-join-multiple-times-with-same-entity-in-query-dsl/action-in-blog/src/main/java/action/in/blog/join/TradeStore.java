package action.in.blog.join;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TradeStore {

    private final JPAQueryFactory jpaQueryFactory;

    public TradeStore(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public List<TradeVO> getTradeInformation() {
        QTradeEntity trade = QTradeEntity.tradeEntity;
        QCompanyEntity provider = new QCompanyEntity("provider");
        QCompanyEntity consumer = new QCompanyEntity("consumer");
        return jpaQueryFactory
                .select(
                        Projections.fields(
                                TradeVO.class,
                                trade.id,
                                trade.providerId,
                                provider.name.as("providerName"),
                                trade.consumerId,
                                consumer.name.as("consumerName"),
                                trade.basedPrice,
                                trade.contractSize
                        )
                )
                .from(trade)
                .leftJoin(provider).on(trade.providerId.eq(provider.providerId))
                .leftJoin(consumer).on(trade.consumerId.eq(consumer.consumerId))
                .fetch();
    }
}
