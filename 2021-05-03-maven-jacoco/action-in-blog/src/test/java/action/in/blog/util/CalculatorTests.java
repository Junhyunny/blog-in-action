package action.in.blog.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CalculatorTests {

    @Test
    void sum() {
        Calculator sut = new Calculator();
        assertThat(sut.sum(1, 2), equalTo(3));
    }

    @Test
    void subtract() {
        Calculator sut = new Calculator();
        assertThat(sut.subtract(1, 2), equalTo(-1));
    }

    @Test
    void isNull() {
        Calculator sut = new Calculator();
        assertThat(sut.isNull(new Object()), equalTo(false));
        assertThat(sut.isNull(null), equalTo(true));
    }
}
