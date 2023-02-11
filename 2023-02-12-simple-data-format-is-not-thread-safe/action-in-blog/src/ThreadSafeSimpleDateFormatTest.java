import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

class ThreadSafeSimpleDateFormat {

    private final SimpleDateFormat simpleDateFormat;

    public ThreadSafeSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    static ThreadSafeSimpleDateFormat applyPattern(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(format);
        return new ThreadSafeSimpleDateFormat(simpleDateFormat);
    }

    String getFormattedDate(Date date) {
        return simpleDateFormat.format(date);
    }
}

public class ThreadSafeSimpleDateFormatTest {

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        Date date = new Date();
        ThreadSafeSimpleDateFormat threadSafeSimpleDateFormat = ThreadSafeSimpleDateFormat.applyPattern("HH:mm:ss.sss");


        CompletableFuture<Void> thread1 = CompletableFuture.runAsync(() -> {
            threadSafeSimpleDateFormat.applyPattern("yyyy-MM-dd");
        });
        CompletableFuture.runAsync(() -> {
            sleep(500);
            System.out.printf("result of formatting - %s", threadSafeSimpleDateFormat.getFormattedDate(date));
        }).join();
        thread1.join();
    }
}
