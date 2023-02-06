package action.in.blog.client;

import action.in.blog.domain.Invitation;

public interface InvitationEventClient {
    void pushInvitationMessage(Invitation invitation);

    void pushInvitationCancelMessage(Invitation invitation);
}
