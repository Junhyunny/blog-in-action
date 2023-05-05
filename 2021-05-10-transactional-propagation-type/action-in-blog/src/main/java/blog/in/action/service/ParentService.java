package blog.in.action.service;

import blog.in.action.domain.Parent;
import blog.in.action.exception.ChildException;
import blog.in.action.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository repository;
    private final ChildService childService;

    void skipExceptionPropagation(Runnable runnable) {
        try {
            runnable.run();
        } catch (ChildException childException) {
            log.error("skip propagation exception");
        }
    }

    @Transactional
    public void createRequiredAndChildRequired(String id) {
        repository.saveAndFlush(new Parent(id));
        skipExceptionPropagation(() -> childService.createRequired(id));
    }

    public void createWithoutTransactionAndChildRequired(String id) {
        repository.saveAndFlush(new Parent(id));
        skipExceptionPropagation(() -> childService.createRequired(id));
    }

    @Transactional
    public void createRequiredAndChildSupports(String id) {
        repository.saveAndFlush(new Parent(id));
        skipExceptionPropagation(() -> childService.createSupports(id));
    }

    public void createWithoutTransactionAndChildSupports(String id) {
        repository.saveAndFlush(new Parent(id));
        skipExceptionPropagation(() -> childService.createSupports(id));
    }

    public void createWithoutTransactionAndChildMandatory(String id) {
        repository.saveAndFlush(new Parent(id));
        childService.createMandatory(id);
    }

    @Transactional
    public void createRequiredAndChildRequiresNew(String id) {
        repository.saveAndFlush(new Parent(id));
        skipExceptionPropagation(() -> childService.createRequiresNew(id));
    }

    @Transactional
    public void createRequiredAndChildNotSupported(String id) {
        repository.saveAndFlush(new Parent(id));
        skipExceptionPropagation(() -> childService.createNotSupported(id));
    }

    @Transactional
    public void createRequiredAndChildNever(String id) {
        repository.saveAndFlush(new Parent(id));
        childService.createNever(id);
    }

    @Transactional
    public void createRequiredAndChildNested(String id) {
        repository.saveAndFlush(new Parent(id));
        childService.createNested(id);
    }
}
