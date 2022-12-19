package action.in.blog.factory;

import org.springframework.stereotype.Service;

@Service
public class FactoryService extends AbstractFactoryService {

    private final FactoryStore factoryStore;

    public FactoryService(FactoryStore factoryStore) {
        this.factoryStore = factoryStore;
    }

    public FactoryEntity findEntityAfterInsert(String name, boolean intentionallyException) {
        return transaction((em) -> {
            factoryStore.createFactoryEntity(em, name);
            if (intentionallyException) {
                throw new RuntimeException("throw intentionallyException");
            }
            return factoryStore.findByName(em, name);
        });
    }
}
