package action.in.blog.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mockStatic;

public class StaticMethodServiceTests {

    LocalDate onApril = LocalDate.of(2022, 4, 1);
    LocalDate onAugust = LocalDate.of(2022, 8, 1);

    MockedStatic<LocalDate> mockLocalDate;

    StaticMethodService service;

    @BeforeEach
    void setUp() {
        service = new StaticMethodService();
        mockLocalDate = mockStatic(LocalDate.class, CALLS_REAL_METHODS);
    }

    @AfterEach
    void afterEach() {
        mockLocalDate.close();
    }

    @Test
    void users_can_get_80_percent_cheaper_price_on_April() {

        mockLocalDate.when(LocalDate::now).thenReturn(onApril);

        assertThat(service.getEventPrice(1000), equalTo(800));
    }

    @Test
    void users_can_get_80_percent_cheaper_price_on_August() {

        mockLocalDate.when(LocalDate::now).thenReturn(onAugust);

        assertThat(service.getEventPrice(1000), equalTo(800));
    }

    @Test
    void users_who_teenager_can_get_80_percent_cheaper_price() {

        LocalDate nineteenYears = LocalDate.of(2002, 4, 2);
        LocalDate twentyYears = LocalDate.of(2002, 4, 1);
        LocalDate nineYears = LocalDate.of(2012, 4, 2);
        LocalDate tenYears = LocalDate.of(2012, 4, 1);

        mockLocalDate.when(LocalDate::now).thenReturn(onApril);

        assertThat(service.getEventPrice(nineteenYears, 1000), equalTo(800));
        assertThat(service.getEventPrice(twentyYears, 1000), equalTo(1000));
        assertThat(service.getEventPrice(tenYears, 1000), equalTo(800));
        assertThat(service.getEventPrice(nineYears, 1000), equalTo(1000));
    }
}
