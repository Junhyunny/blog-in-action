package action.in.blog.store;

import action.in.blog.domain.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class FooStore {

    public void createFoo(AuthenticatedUser user) {
        log.info("using {} for creating foo", user);
    }
}
