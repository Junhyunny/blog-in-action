import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

class ThreadNotSafeSimpleDateFormat {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    void applyPattern(String format) {
        simpleDateFormat.applyPattern(format);
    }

    String getFormattedDate(Date date) {
        return simpleDateFormat.format(date);
    }
}

public class ThreadNotSafeSimpleDateFormatTest {

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        Date date = new Date();
        ThreadNotSafeSimpleDateFormat threadNotSafeSimpleDateFormat = new ThreadNotSafeSimpleDateFormat();
        threadNotSafeSimpleDateFormat.applyPattern("HH:mm:ss.sss");


        CompletableFuture<Void> thread1 = CompletableFuture.runAsync(() -> {
            threadNotSafeSimpleDateFormat.applyPattern("yyyy-MM-dd");
        });
        CompletableFuture.runAsync(() -> {
            sleep(500);
            System.out.printf("result of formatting - %s", threadNotSafeSimpleDateFormat.getFormattedDate(date));
        }).join();
        thread1.join();
    }
}