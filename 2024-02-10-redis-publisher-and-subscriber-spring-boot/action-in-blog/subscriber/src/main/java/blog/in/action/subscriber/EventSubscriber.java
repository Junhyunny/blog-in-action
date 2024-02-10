package blog.in.action.subscriber;

import org.springframework.data.redis.connection.MessageListener;

public interface EventSubscriber extends MessageListener {

    String channelName();
}
