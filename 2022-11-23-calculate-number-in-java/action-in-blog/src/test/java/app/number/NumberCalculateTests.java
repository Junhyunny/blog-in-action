package app.number;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class NumberCalculateTests {

    @Test
    void wrong_number_calculate() {

        double number = 0;
        for (int i = 0; i < 1000; i++) {
            number += 0.1;
        }


        assertThat(number, not(100.0)); // 99.9999999999986
        assertThat(1.03 - 0.42, not(0.61)); // 0.6100000000000001
        assertThat(1.00 - 9 * 0.10, not(0.1)); // 0.09999999999999998
    }

    @Test
    void correct_number_calculate() {

        BigDecimal number = BigDecimal.ZERO;
        for (int i = 0; i < 1000; i++) {
            number = number.add(BigDecimal.valueOf(0.1));
        }


        assertThat(number, equalTo(BigDecimal.valueOf(100.0)));
        assertThat(BigDecimal.valueOf(1.03).subtract(BigDecimal.valueOf(0.42)), equalTo(BigDecimal.valueOf(0.61)));
        assertThat(BigDecimal.valueOf(1.00).subtract(
                BigDecimal.valueOf(9).multiply(BigDecimal.valueOf(0.10))
        ), equalTo(BigDecimal.valueOf(0.1)));
    }
}
