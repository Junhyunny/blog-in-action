package cloud.in.action.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cloud.in.action.proxy.BServiceFeinClient;

@RestController
public class AServiceController {

	private final BServiceFeinClient client;

	public AServiceController(BServiceFeinClient client) {
		this.client = client;
	}

	@GetMapping(path = "/call-b-service")
	public String requestCallBService() {
		return client.requestInformation();
	}
}
