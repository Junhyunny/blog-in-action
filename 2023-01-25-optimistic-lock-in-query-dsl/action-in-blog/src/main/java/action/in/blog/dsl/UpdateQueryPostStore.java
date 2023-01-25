package action.in.blog.dsl;

import action.in.blog.dsl.entity.PostEntity;
import action.in.blog.dsl.entity.QPostEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

@Repository
public class UpdateQueryPostStore {

    private final JPAQueryFactory jpaQueryFactory;

    public UpdateQueryPostStore(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    private boolean isNotExisted(BooleanExpression whereClause) {
        QPostEntity postEntity = QPostEntity.postEntity;
        return jpaQueryFactory
                .selectFrom(postEntity)
                .where(whereClause)
                .fetchFirst() == null;
    }

    @Transactional
    public void updateEntity(PostEntity entity) {
        QPostEntity postEntity = QPostEntity.postEntity;
        BooleanExpression whereClause = postEntity.id.eq(entity.getId());
        long result = jpaQueryFactory
                .update(postEntity)
                .set(postEntity.title, entity.getTitle())
                .set(postEntity.contents, entity.getContents())
                .set(postEntity.versionNo, entity.getVersionNo() + 1)
                .where(whereClause, postEntity.versionNo.eq(entity.getVersionNo()))
                .execute();
        if (result != 0) {
            return;
        }
        if (isNotExisted(whereClause)) {
            throw new EntityNotFoundException("entity is not existed");
        } else {
            throw new OptimisticLockException("entity should be updated by other transaction");
        }
    }
}
