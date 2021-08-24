package blog.in.action;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class ImmutableTest {

    @Test
    public void test() {
        String str = "A";
        log.info("값 변경 전 객체 주소: " + System.identityHashCode(str) + ", 객체 value 값: " + str);
        // str = str + "B";
        str = str.concat("B");
        log.info("값 변경 후 객체 주소: " + System.identityHashCode(str) + ", 객체 value 값: " + str);
    }

    @Test
    public void test_integer() {
        Integer integer = Integer.valueOf(0);
        log.info("값 변경 전 객체 주소: " + System.identityHashCode(integer) + ", 객체 value 값: " + integer);
        integer = integer + 2;
        log.info("값 변경 후 객체 주소: " + System.identityHashCode(integer) + ", 객체 value 값: " + integer);
    }
}

class ImmutableObject {

    private final int value;

    public ImmutableObject(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}