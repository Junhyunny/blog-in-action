package action.in.blog.controller;

import action.in.blog.client.InvitationEventClient;
import action.in.blog.domain.Invitation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class InvitationController {

    private final InvitationEventClient invitationEventClient;

    @PostMapping("/invitation")
    public void createPost(@RequestBody Invitation invitation) {
        invitationEventClient.pushInvitationMessage(invitation);
    }

    @PostMapping("/invitation/cancel")
    public void updatePost(@RequestBody Invitation invitation) {
        invitationEventClient.pushInvitationCancelMessage(invitation);
    }
}
