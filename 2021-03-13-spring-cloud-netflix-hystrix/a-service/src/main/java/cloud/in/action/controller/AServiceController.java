package cloud.in.action.controller;

import cloud.in.action.proxy.BServiceFeinClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class AServiceController {

    private final BServiceFeinClient client;

    public AServiceController(BServiceFeinClient client) {
        this.client = client;
    }

    @GetMapping(value = "/timeout")
    public String requestWithTimeout() {
        return client.requestWithTimeout();
    }

    @GetMapping(value = "/exception")
    public String requestWithException() {
        return client.requestWithException();
    }
}
