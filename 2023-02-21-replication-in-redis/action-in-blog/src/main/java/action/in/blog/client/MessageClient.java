package action.in.blog.client;

import action.in.blog.domain.MessageGroup;

public interface MessageClient {

    long getUnreadMessagesSize();

    void pushMessage(String message);

    MessageGroup readMessageGroup();

    void flushUnreadMessages();
}
