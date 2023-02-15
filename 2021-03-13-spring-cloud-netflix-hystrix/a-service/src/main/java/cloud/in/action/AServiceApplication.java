package cloud.in.action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AServiceApplication.class, args);
    }

}
