package action.in.blog.controller;

import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

record GeneratedKeyResponse(String username, UUID key) {
}

@RestController
public class TargetController {

    private boolean is80PercentSuccess() {
        return Math.random() < 0.8;
    }

    @Retryable(retryFor = RuntimeException.class)
    @GetMapping("/target")
    public GeneratedKeyResponse generate(@RequestParam String username) {
        if (is80PercentSuccess()) {
            return new GeneratedKeyResponse(
                    username,
                    UUID.randomUUID()
            );
        } else {
            throw new RuntimeException("Something went wrong");
        }
    }
}
