package action.in.blog.dsl;

import action.in.blog.dsl.entity.PostEntity;
import action.in.blog.dsl.entity.QPostEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

@Slf4j
@Repository
public class DirtyCheckPostStore {

    private final JPAQueryFactory jpaQueryFactory;

    public DirtyCheckPostStore(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    private void waitMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Transactional
    public void updateEntityWithLongTransaction(PostEntity entity) {
        updateEntity(entity);
        waitMillis(1000);
    }

    @Transactional
    public void updateEntity(PostEntity entity) {
        QPostEntity postEntity = QPostEntity.postEntity;
        PostEntity targetEntity = jpaQueryFactory
                .selectFrom(postEntity)
                .where(postEntity.id.eq(entity.getId()))
                .setLockMode(LockModeType.OPTIMISTIC)
                .fetchOne();
        targetEntity.setTitle(entity.getTitle());
        targetEntity.setContents(entity.getContents());
    }
}
