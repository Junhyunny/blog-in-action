package action.in.blog;

import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ActionInBlogApplicationTests {

    @Test
    void date_of_year_exceeding_error() {

        String locale = "Asia/Seoul";
        ZoneId zoneId = ZoneId.of(locale);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(locale));
        calendar.set(2022, 11, 25);
        Date date = calendar.getTime();
        Instant instant = Instant.ofEpochMilli(date.getTime());


        Throwable throwable = assertThrows(DateTimeException.class, () -> {
            ZonedDateTime.ofInstant(instant, zoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-DD"));
        });
        assertThat(throwable.getMessage(), equalTo("Field DayOfYear cannot be printed as the value 359 exceeds the maximum print width of 2"));
    }

    @Test
    void format_date() {

        String locale = "Asia/Seoul";
        ZoneId zoneId = ZoneId.of(locale);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(locale));
        calendar.set(2022, 11, 25);
        Date date = calendar.getTime();
        Instant instant = Instant.ofEpochMilli(date.getTime());


        String result = ZonedDateTime.ofInstant(instant, zoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dayOfYearResult = ZonedDateTime.ofInstant(instant, zoneId).format(DateTimeFormatter.ofPattern("DDD"));


        assertThat(result, equalTo("2022-12-25"));
        assertThat(dayOfYearResult, equalTo("359"));
    }

    @Test
    void without_error_when_using_jdk_11() {

        String locale = "Asia/Seoul";
        ZoneId zoneId = ZoneId.of(locale);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(locale));
        calendar.set(2022, 11, 25);
        Date date = calendar.getTime();
        Instant instant = Instant.ofEpochMilli(date.getTime());


        String dayOfYearResult = ZonedDateTime.ofInstant(instant, zoneId).format(DateTimeFormatter.ofPattern("D"));


        assertThat(dayOfYearResult, equalTo("359"));
    }
}
