package action.in.blog.domain;

public enum QueueChannel {

    INVITATION;

    public String of(String key) {
        return String.format("%s-%s", INVITATION.name(), key);
    }
}
