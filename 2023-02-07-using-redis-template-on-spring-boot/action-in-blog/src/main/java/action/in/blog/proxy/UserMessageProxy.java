package action.in.blog.proxy;

import action.in.blog.domain.InvitationMessage;

import java.util.List;

public interface UserMessageProxy {
    List<InvitationMessage> getInvitationMessage(String userId);
}