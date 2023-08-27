package action.in.blog;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Helper {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transaction(Runnable runnable) {
        runnable.run();
    }
}
