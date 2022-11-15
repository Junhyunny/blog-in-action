package app.date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DateTests {

    @Test
    @DisplayName("Date 객체는 다중 스레드에 의해 변경되는 경우 정상적인 값을 얻지 못한다.")
    void date_instance_is_not_thread_safe() {

        final Date date = new Date();

        long unixTime = date.getTime();

        List<CompletableFuture> tasks = new ArrayList<>();
        tasks.add(CompletableFuture.runAsync(() -> {
            for (int index = 0; index < 100; index++) {
                date.setTime(date.getTime() - 1);
            }
        }));
        tasks.add(CompletableFuture.runAsync(() -> {
            for (int index = 0; index < 100; index++) {
                date.setTime(date.getTime() + 1);
            }
        }));
        tasks.stream().forEach(task -> task.join());


        assertThat(unixTime, equalTo(date.getTime()));
    }

    @Test
    @DisplayName("Date 의 년도는 1900을 기준으로 더하거나 빼서 사용한다.")
    void year_of_date_starts_based_on_1900() {

        Date date = new Date(122, 10, 14, 4, 30, 00);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        assertThat(simpleDateFormat.format(date), equalTo("2022-11-14 04:30:00"));
    }

    @Test
    @DisplayName("Date 의 달(month)은 0부터 시작이다.")
    void month_of_date_starts_from_zero() {

        Date date = new Date(122, 11, 14, 4, 30, 00);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        assertThat(simpleDateFormat.format(date), equalTo("2022-12-14 04:30:00"));
    }
}
