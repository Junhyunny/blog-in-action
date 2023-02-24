package action.in.blog.client;

import action.in.blog.domain.Message;
import action.in.blog.domain.MessageGroup;
import action.in.blog.domain.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RedisMessageClient implements MessageClient {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public long getUnreadMessagesSize() {
        return redisTemplate.opsForList().size(Queue.UNREAD.name());
    }

    @Override
    public void pushMessage(String message) {
        Message body = Message.builder()
                .id(UUID.randomUUID().toString())
                .message(message)
                .build();
        redisTemplate.opsForList().rightPush(Queue.UNREAD.name(), body);
    }

    @Override
    public MessageGroup readMessageGroup() {

        long unreadQueueSize = redisTemplate.opsForList().size(Queue.UNREAD.name());
        List<Message> unreadMessages = (List) redisTemplate.opsForList().range(Queue.UNREAD.name(), 0, unreadQueueSize);

        long readQueueSize = redisTemplate.opsForList().size(Queue.READ.name());
        List<Message> readMessages = (List) redisTemplate.opsForList().range(Queue.READ.name(), 0, readQueueSize);

        return MessageGroup.builder()
                .unreadMessages(unreadMessages)
                .readMessages(readMessages)
                .build();
    }

    @Override
    public void flushUnreadMessages() {
        long unreadQueueSize = redisTemplate.opsForList().size(Queue.UNREAD.name());
        List<Message> unreadMessages = (List) redisTemplate.opsForList().rightPop(Queue.UNREAD.name(), unreadQueueSize);
        if (unreadMessages.size() != 0) {
            redisTemplate.opsForList().rightPushAll(Queue.READ.name(), unreadMessages.toArray());
        }
    }
}
