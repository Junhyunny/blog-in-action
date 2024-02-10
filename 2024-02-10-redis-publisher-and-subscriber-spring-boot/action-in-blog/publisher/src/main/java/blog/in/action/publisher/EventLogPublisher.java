package blog.in.action.publisher;

import blog.in.action.domain.Channel;
import blog.in.action.domain.EventLog;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventLogPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public EventLogPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(Channel channel, EventLog event) {
        try {
            redisTemplate.convertAndSend(channel.name(), event);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
