package app.date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import static java.util.Calendar.THURSDAY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CalendarTests {

    @Test
    @DisplayName("Calendar 객체는 다중 스레드에 의해 변경되는 경우 정상적인 값을 얻지 못한다.")
    void calendar_instance_is_not_thread_safe() {

        Calendar calendar = Calendar.getInstance();

        long unixTime = calendar.getTimeInMillis();

        List<CompletableFuture> tasks = new ArrayList<>();
        tasks.add(CompletableFuture.runAsync(() -> {
            for (int index = 0; index < 100; index++) {
                calendar.setTimeInMillis(calendar.getTimeInMillis() - 1);
            }
        }));
        tasks.add(CompletableFuture.runAsync(() -> {
            for (int index = 0; index < 100; index++) {
                calendar.setTimeInMillis(calendar.getTimeInMillis() + 1);
            }
        }));
        tasks.stream().forEach(task -> task.join());


        assertThat(unixTime, equalTo(calendar.getTimeInMillis()));
    }

    @Test
    @DisplayName("Calendar 클래스는 무분별한 정수형 상수들을 제공하기 때문에 코드가 직관적이지 않다.")
    void calendar_class_provides_too_many_integer_constants() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 10, 15);


        calendar.add(Calendar.DATE, 1);
        calendar.add(Calendar.THURSDAY, 1);


        assertThat(calendar.get(Calendar.DATE), equalTo(17));
    }

    @Test
    @DisplayName("Calendar 클래스는 월 지정을 0부터 시작한다.")
    void calendar_class_starts_zero_when_setup_month() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 10, 15);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        assertThat(simpleDateFormat.format(calendar.getTimeInMillis()), equalTo("2022-11-15"));
    }

    @Test
    @DisplayName("Calendar 클래스는 타임 존 지정 시 오류가 나지 않는다.")
    void calendar_class_do_not_throw_exception_when_setup_timezone() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Seoul/Asia"));


        assertThat(calendar.getTimeZone(), equalTo(TimeZone.getTimeZone("GMT")));
    }

}
