import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleDateFormatParseTest {

    public static void main(String[] args) {

        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int index = 0; index < 100; index++) {
            executorService.submit(() -> {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    System.out.printf("Successfully Parsing - %s%n", simpleDateFormat.parse("2023-02-12T02:35:00"));
                } catch (Exception e) {
                    System.out.printf("Parse Error - %s%n", e.getMessage());
                }
            });
        }

        executorService.shutdown();
    }
}
