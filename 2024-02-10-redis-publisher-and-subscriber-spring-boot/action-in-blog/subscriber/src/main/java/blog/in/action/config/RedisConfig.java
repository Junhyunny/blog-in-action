package blog.in.action.config;

import blog.in.action.subscriber.EventSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.List;

@Configuration
public class RedisConfig {

    public final List<EventSubscriber> redisMessageSubscribers;

    public RedisConfig(List<EventSubscriber> redisMessageSubscribers) {
        this.redisMessageSubscribers = redisMessageSubscribers;
    }

    @Bean
    public RedisMessageListenerContainer redisListenerContainer(
            RedisConnectionFactory connectionFactory
    ) {
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        for (var eventSubscriber : redisMessageSubscribers) {
            container.addMessageListener(
                    new MessageListenerAdapter(eventSubscriber),
                    new ChannelTopic(eventSubscriber.channelName())
            );
        }
        return container;
    }
}
