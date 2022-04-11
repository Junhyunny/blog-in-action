package action.in.blog.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StaticMethodServiceTests {

    LocalDate onApril = LocalDate.of(2022, 4, 1);
    LocalDate onAugust = LocalDate.of(2022, 8, 1);

    MockedStatic<LocalDate> mockLocalDate;

    StaticMethodService service;

    @BeforeEach
    void setUp() {
        mockLocalDate = Mockito.mockStatic(LocalDate.class);
        service = new StaticMethodService();
    }

    @AfterEach
    void afterEach() {
        mockLocalDate.close();
    }

    @Test
    void given1000AndAprilDate_whenGetEventPrice_thenReturn800() {

        mockLocalDate.when(LocalDate::now).thenReturn(onApril);

        assertThat(service.getEventPrice(1000), equalTo(800));
    }

    @Test
    void given1000AndAugustDate_whenGetEventPrice_thenReturn800() {

        mockLocalDate.when(LocalDate::now).thenReturn(onAugust);

        assertThat(service.getEventPrice(1000), equalTo(800));
    }
}
