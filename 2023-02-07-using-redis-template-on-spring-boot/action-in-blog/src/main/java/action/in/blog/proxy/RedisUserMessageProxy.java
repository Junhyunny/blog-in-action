package action.in.blog.proxy;

import action.in.blog.domain.InvitationMessage;
import action.in.blog.domain.QueueChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("!local")
@Component
@RequiredArgsConstructor
public class RedisUserMessageProxy implements UserMessageProxy {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<InvitationMessage> getInvitationMessage(String userId) {
        String channel = QueueChannel.INVITATION.of(userId);
        long size = redisTemplate.opsForList().size(channel);
        return (List) redisTemplate.opsForList().leftPop(channel, size);
    }
}


