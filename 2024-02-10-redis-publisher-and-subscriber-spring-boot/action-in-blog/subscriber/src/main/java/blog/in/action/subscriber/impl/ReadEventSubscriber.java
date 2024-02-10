package blog.in.action.subscriber.impl;

import blog.in.action.domain.Channel;
import blog.in.action.subscriber.EventSubscriber;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

@Component
public class ReadEventSubscriber implements EventSubscriber {

    @Override
    public String channelName() {
        return Channel.TODO_READ.name();
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.printf("ReadEventSubscriber channel - %s%n", new String(message.getChannel()));
        System.out.printf("body - %s%n", new String(message.getBody()));
    }
}
