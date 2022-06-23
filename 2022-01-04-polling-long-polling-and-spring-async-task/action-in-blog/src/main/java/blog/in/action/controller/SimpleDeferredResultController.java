package blog.in.action.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

@Log4j2
@RestController
public class SimpleDeferredResultController {

    @GetMapping("/data")
    public DeferredResult<String> getData() {
        DeferredResult<String> result = new DeferredResult<>();
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            }
            result.setResult("HelloWorld");
        });
        return result;
    }
}