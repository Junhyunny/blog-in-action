package action.in.blog.service;

import action.in.blog.domain.AuthenticatedUser;
import action.in.blog.store.FooStore;
import org.springframework.stereotype.Service;

@Service
public class FooService {

    private final FooStore fooStore;

    public FooService(FooStore fooStore) {
        this.fooStore = fooStore;
    }

    public void createFoo(AuthenticatedUser user) {
        fooStore.createFoo(user);
    }
}
