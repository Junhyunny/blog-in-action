package action.in.blog.store;

import action.in.blog.util.AuthenticatedUserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class BarStore {

    public void createBar() {
        log.info("using {} for creating bar", AuthenticatedUserHolder.get());
    }
}
