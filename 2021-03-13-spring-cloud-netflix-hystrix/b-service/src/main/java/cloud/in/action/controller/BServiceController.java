package cloud.in.action.controller;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class BServiceController {

	@GetMapping(value = "/timeout")
	public String requestWithTimeout() {
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return "no time out occur";
	}

	@GetMapping(value = "/exception")
	public String requestWithException() {
		if (new Random().nextBoolean()) {
			throw new RuntimeException("exception occur");
		}
		return "no exception occur";
	}
}
