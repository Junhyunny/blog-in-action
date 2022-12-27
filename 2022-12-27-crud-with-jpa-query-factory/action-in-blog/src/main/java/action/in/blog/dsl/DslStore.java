package action.in.blog.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static action.in.blog.dsl.QDslEntity.dslEntity;

@Repository
public class DslStore {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public DslStore(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Transactional
    public void createEntity(DslEntity entity) {
        entityManager.persist(entity);
//        jpaQueryFactory
//                .insert(dslEntity)
//                .set(dslEntity.someValue, entity.getSomeValue())
//                .execute();
    }

    public List<DslEntity> getEntityByContains(String value) {
        return jpaQueryFactory
                .selectFrom(dslEntity)
                .where(dslEntity.someValue.containsIgnoreCase(value))
                .fetch();
    }

    @Transactional
    public void updateEntity(DslEntity entity) {
        jpaQueryFactory
                .update(dslEntity)
                .set(dslEntity.someValue, entity.getSomeValue())
                .where(dslEntity.id.eq(entity.getId()))
                .execute();
    }

    @Transactional
    public void deleteEntity(long id) {
        jpaQueryFactory
                .delete(dslEntity)
                .where(dslEntity.id.eq(id))
                .execute();
    }
}
