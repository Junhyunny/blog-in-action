package action.in.blog.annotation;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class PcAnnotationStore {

    @PersistenceContext
    private EntityManager em;

    public void createFactoryEntity(String name) {
        em.persist(PcAnnotationEntity.builder()
                .name(name)
                .build());
    }

    public PcAnnotationEntity findByName(String name) {
        TypedQuery<PcAnnotationEntity> query = em.createQuery("select pc from PcAnnotationEntity pc where pc.name = :name", PcAnnotationEntity.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
