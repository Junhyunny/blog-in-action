package blog.in.action.di.recycle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecycleCatchTest {

    // 다른 테스트시 주석을 해야 순환참조가 발생하지 않습니다.
    // @Autowired
    CComponent cComponent;

    @Test
    public void test() {
        cComponent.doThing();
    }
}

// 다른 테스트시 주석을 해야 순환참조가 발생하지 않습니다.
// @Component
class CComponent {

    final DComponent dComponent;

    public CComponent(DComponent dComponent) {
        this.dComponent = dComponent;
    }

    void doThing() {
        dComponent.doThing();
    }
}

// 다른 테스트시 주석을 해야 순환참조가 발생하지 않습니다.
// @Component
class DComponent {

    final CComponent cComponent;

    public DComponent(CComponent cComponent) {
        this.cComponent = cComponent;
    }

    void doThing() {
        cComponent.doThing();
    }
}
