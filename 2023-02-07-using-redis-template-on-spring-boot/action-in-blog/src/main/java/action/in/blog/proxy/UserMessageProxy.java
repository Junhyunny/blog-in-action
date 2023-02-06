package action.in.blog.proxy;

import action.in.blog.domain.Message;

import java.util.List;

public interface UserMessageProxy {
    List<Message> getMessagesForUser(String id);
}
