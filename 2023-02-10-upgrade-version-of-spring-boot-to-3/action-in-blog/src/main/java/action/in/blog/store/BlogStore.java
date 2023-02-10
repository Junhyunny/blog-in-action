package action.in.blog.store;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class BlogStore {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public BlogStore(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }
}
