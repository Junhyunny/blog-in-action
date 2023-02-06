package action.in.blog.proxy;

import action.in.blog.domain.Message;
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
    public List<Message> getMessagesForUser(String id) {
        long size = redisTemplate.opsForList().size(id);
        return (List) redisTemplate.opsForList().leftPop(id, size);
    }
}


