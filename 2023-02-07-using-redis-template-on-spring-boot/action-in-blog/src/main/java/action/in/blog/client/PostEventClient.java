package action.in.blog.client;

public interface PostEventClient {
    void pushPostCreationMessage(String userId);

    void pushPostUpdateMessage(String userId);
}
