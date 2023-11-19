package action.in.blog.controller;

import action.in.blog.service.CollectService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api")
public class CollectController {

    private final CollectService collectService;

    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

    @PostMapping("/cards/{cardId}")
    public void collect(Principal principal, @PathVariable String cardId) {
        collectService.collect(principal.getName(), cardId);
    }
}
