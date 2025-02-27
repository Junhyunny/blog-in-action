package blog.in.action.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/users")
    public List<String> searchByJoinDate(LocalDate beginDate, LocalDate endDate) {
        return Arrays.asList(
                String.format("user1 %s", beginDate),
                String.format("user2 %s", endDate)
        );
    }
}
