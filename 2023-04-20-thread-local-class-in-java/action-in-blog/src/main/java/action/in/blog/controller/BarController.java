package action.in.blog.controller;

import action.in.blog.service.BarService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarController {

    private final BarService barService;

    public BarController(BarService barService) {
        this.barService = barService;
    }

    @PostMapping("/bar")
    public void createBar() {
        barService.createBar();
    }
}
