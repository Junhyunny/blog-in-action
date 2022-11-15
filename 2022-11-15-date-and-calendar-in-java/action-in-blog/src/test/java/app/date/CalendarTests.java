package app.date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
}
