package blog.in.action.controller;

import blog.in.action.client.AClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ASleuthController {

    private final AClient aClient;

    @GetMapping("/a-path")
    public String aPath() {
        return "A-SERVICE";
    }
}
