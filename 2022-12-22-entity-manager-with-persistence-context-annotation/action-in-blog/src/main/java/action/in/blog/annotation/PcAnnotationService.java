package action.in.blog.annotation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PcAnnotationService {

    private final PcAnnotationStore pcAnnotationStore;

    public PcAnnotationService(PcAnnotationStore pcAnnotationStore) {
        this.pcAnnotationStore = pcAnnotationStore;
    }

    @Transactional
    public PcAnnotationEntity findEntityAfterInsert(String name, boolean intentionallyException) {
        pcAnnotationStore.createFactoryEntity(name);
        if (intentionallyException) {
            throw new RuntimeException("throw intentionallyException");
        }
        return pcAnnotationStore.findByName(name);
    }
}
