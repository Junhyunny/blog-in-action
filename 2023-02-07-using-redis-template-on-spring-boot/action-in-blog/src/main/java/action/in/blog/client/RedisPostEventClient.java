package action.in.blog.client;

import action.in.blog.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPostEventClient implements PostEventClient {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void pushPostCreationMessage(String userId) {
        Message message = Message.builder()
                .title("New Post")
                .content("Someone create post")
                .build();
        redisTemplate.opsForList().rightPush(userId, message);
    }

    @Override
    public void pushPostUpdateMessage(String userId) {
        Message message = Message.builder()
                .title("Update Post")
                .content("Someone update post")
                .build();
        redisTemplate.opsForList().rightPush(userId, message);
    }
}
