package cloud.in.action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BServiceApplication.class, args);
	}

}
