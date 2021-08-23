package cloud.in.action;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.gson.GsonBuilder;

import lombok.extern.log4j.Log4j2;

@FeignClient(name = "a-service")
interface ASerivceClient {

	@GetMapping(path = "/call-b-service")
	String requestCallBService();
}

@Log4j2
@SpringBootTest
class AServiceApplicationTests {

	@Autowired
	private ASerivceClient client;

	@Test
	void test() {
		Map<String, Integer> result = new HashMap<>();
		for (int index = 0; index < 1000; index++) {
			String response = client.requestCallBService();
			if (result.containsKey(response)) {
				result.put(response, result.get(response) + 1);
			} else {
				result.put(response, Integer.valueOf(1));
			}
		}
		log.info("result: " + new GsonBuilder().setPrettyPrinting().create().toJson(result));
	}

}
