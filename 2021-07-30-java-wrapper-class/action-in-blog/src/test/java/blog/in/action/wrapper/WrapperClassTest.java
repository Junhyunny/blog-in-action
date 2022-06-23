package blog.in.action.wrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class WrapperClassTest {

    @Test
    public void test_comparisonOperatorTrue_equalOperatorFalse_equalsMethodTrue() {
        Integer a = new Integer(1);
        Integer b = 2;
        Integer c = new Integer(1);
        assertThat(a < b).isTrue();
        assertThat(a == c).isFalse();
        assertThat(a.equals(c)).isTrue();
    }

    @Test
    public void test_comparisonOperatorTrue_equalOperatorTrueWithValueOf() {
        Integer a = Integer.valueOf(1);
        Integer b = Integer.valueOf(1);
        log.info(System.identityHashCode(a));
        log.info(System.identityHashCode(b));
        assertThat(a == b).isTrue();
    }

    @Test
    public void test_throwNullPointException() {
        Integer a = null;
        assertThrows(NullPointerException.class, () -> {
            if (a == 1) {
                log.info("do something");
            }
        });
    }
}
