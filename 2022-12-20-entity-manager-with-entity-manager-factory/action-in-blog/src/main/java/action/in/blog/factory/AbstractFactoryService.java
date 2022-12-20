package action.in.blog.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

public abstract class AbstractFactoryService {

    @PersistenceUnit
    private EntityManagerFactory factory;

    protected <V> V transaction(EntityManagerCallable<V> callable) {
        return transaction(callable, false);
    }

    protected <V> V transaction(EntityManagerCallable<V> callable, boolean readonly) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            V ret = callable.run(em);
            if (readonly) {
                transaction.rollback();
            } else {
                transaction.commit();
            }
            return ret;
        } catch (Throwable e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    protected interface EntityManagerCallable<V> {
        V run(EntityManager em);
    }
}
