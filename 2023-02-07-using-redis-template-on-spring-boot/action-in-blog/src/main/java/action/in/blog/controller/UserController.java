package action.in.blog.controller;

import action.in.blog.domain.Message;
import action.in.blog.proxy.UserMessageProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserMessageProxy userMessageProxy;

    @GetMapping("/user/messages/{id}")
    public List<Message> getMessagesForUser(@PathVariable("id") String id) {
        return userMessageProxy.getMessagesForUser(id);
    }
}
