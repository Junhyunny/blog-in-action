package blog.in.action.service;

import blog.in.action.domain.Child;
import blog.in.action.exception.ChildException;
import blog.in.action.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository repository;

    @Transactional
    public void createRequired(String id) {
        repository.saveAndFlush(new Child(id));
        throw new ChildException();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void createSupports(String id) {
        repository.saveAndFlush(new Child(id));
        throw new ChildException();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void createMandatory(String id) {
        repository.saveAndFlush(new Child(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createRequiresNew(String id) {
        repository.saveAndFlush(new Child(id));
        throw new ChildException();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createNotSupported(String id) {
        repository.saveAndFlush(new Child(id));
        throw new ChildException();
    }

    @Transactional(propagation = Propagation.NEVER)
    public void createNever(String id) {
        repository.saveAndFlush(new Child(id));
    }

    @Transactional(propagation = Propagation.NESTED)
    public void createNested(String id) {
        repository.saveAndFlush(new Child(id));
    }
}
