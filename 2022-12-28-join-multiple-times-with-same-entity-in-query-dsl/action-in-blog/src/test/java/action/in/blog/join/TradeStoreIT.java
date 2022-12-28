package action.in.blog.join;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
public class TradeStoreIT {

    @PersistenceUnit
    EntityManagerFactory factory;

    void transaction(Consumer<EntityManager> consumer) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            consumer.accept(em);
        } catch (Exception ex) {
            throw ex;
        } finally {
            transaction.rollback();
            em.close();
        }
    }

    void flushAndClear(EntityManager em) {
        em.flush();
        em.clear();
    }

    @Test
    void getTradeInformation() {
        transaction(em -> {
            em.persist(
                    CompanyEntity.builder()
                            .bizRegistrationNumber(UUID.randomUUID().toString())
                            .name("(주) Alpha Company")
                            .providerId("0001")
                            .consumerId("5001")
                            .build()
            );
            em.persist(
                    CompanyEntity.builder()
                            .bizRegistrationNumber(UUID.randomUUID().toString())
                            .name("(주) Beta Company")
                            .providerId("0002")
                            .consumerId("5002")
                            .build()
            );
            em.persist(
                    TradeEntity.builder()
                            .providerId("0001")
                            .consumerId("5002")
                            .basedPrice(2500)
                            .contractSize(5)
                            .build()
            );
            flushAndClear(em);


            TradeStore sut = new TradeStore(em);
            List<TradeVO> result = sut.getTradeInformation();


            assertThat(result.size(), equalTo(1));
            TradeVO firstTrade = result.get(0);
            assertThat(firstTrade.getProviderId(), equalTo("0001"));
            assertThat(firstTrade.getProviderName(), equalTo("(주) Alpha Company"));
            assertThat(firstTrade.getConsumerId(), equalTo("5002"));
            assertThat(firstTrade.getConsumerName(), equalTo("(주) Beta Company"));
            assertThat(firstTrade.getBasedPrice(), equalTo(2500));
            assertThat(firstTrade.getContractSize(), equalTo(5));
        });
    }
}
