package action.in.blog;

public interface SessionRegistry {

    Object getSession(String sessionId);

    void putSession(String sessionId, Object session);

    void deleteSession(String sessionId);
}
