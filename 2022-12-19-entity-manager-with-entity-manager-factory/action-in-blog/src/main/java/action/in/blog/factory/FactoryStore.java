package action.in.blog.factory;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class FactoryStore {

    public void createFactoryEntity(EntityManager em, String name) {
        em.persist(FactoryEntity.builder()
                .name(name)
                .build());
    }

    public FactoryEntity findByName(EntityManager em, String name) {
        TypedQuery<FactoryEntity> query = em.createQuery("select f from FactoryEntity f where f.name = :name", FactoryEntity.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
