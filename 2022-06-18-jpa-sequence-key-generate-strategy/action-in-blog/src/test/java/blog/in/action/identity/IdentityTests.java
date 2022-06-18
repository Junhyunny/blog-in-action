package blog.in.action.identity;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@Log4j2
@DataJpaTest
public class IdentityTests {

    @Autowired
    IdentityRepository repository;

    @Test
    void test() {

        log.info("추가");
        for (int index = 0; index < 3; index++) {
            log.info("before save");
            repository.save(new IdentityEntity());
            log.info("after save");
        }

        log.info("조회");
        List<IdentityEntity> entities = repository.findAll();
        for (IdentityEntity entity : entities) {
            log.info(entity.getId());
        }
    }
}
