package action.in.blog.client;

import action.in.blog.domain.Invitation;
import action.in.blog.domain.InvitationMessage;
import action.in.blog.domain.InvitationStatus;
import action.in.blog.domain.QueueChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisInvitationEventClient implements InvitationEventClient {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void pushInvitationMessage(Invitation invitation) {
        String key = invitation.getInvitee();
        InvitationMessage message = InvitationMessage.builder()
                .inviter(invitation.getInviter())
                .status(InvitationStatus.INVITATION)
                .build();
        redisTemplate.opsForList().rightPush(QueueChannel.INVITATION.of(key), message);
    }

    @Override
    public void pushInvitationCancelMessage(Invitation invitation) {
        String key = invitation.getInvitee();
        InvitationMessage message = InvitationMessage.builder()
                .inviter(invitation.getInviter())
                .status(InvitationStatus.INVITATION_CANCEL)
                .build();
        redisTemplate.opsForList().rightPush(QueueChannel.INVITATION.of(key), message);
    }
}
