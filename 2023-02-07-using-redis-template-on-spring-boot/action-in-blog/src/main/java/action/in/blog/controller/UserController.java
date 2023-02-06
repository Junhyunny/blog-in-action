package action.in.blog.controller;

import action.in.blog.domain.InvitationMessage;
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

    @GetMapping("/user/messages/{userId}")
    public List<InvitationMessage> getInvitationMessage(@PathVariable("userId") String userId) {
        return userMessageProxy.getInvitationMessage(userId);
    }
}
