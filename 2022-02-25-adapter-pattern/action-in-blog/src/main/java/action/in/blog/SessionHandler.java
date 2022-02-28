package action.in.blog;

public class SessionHandler {

    private final SessionRegistry sessionRegistry;

    public SessionHandler(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    public Object getSession(String sessionId) {
        Object session = sessionRegistry.getSession(sessionId);
        if (session == null) {
            throw new RuntimeException("session does not exist");
        }
        return session;
    }

    public void putSession(String sessionId, Object session) {
        try {
            sessionRegistry.putSession(sessionId, session);
        } catch (RuntimeException re) {
            new RuntimeException("error when putting session", re);
        }
    }

    public void deleteSession(String sessionId) {
        try {
            sessionRegistry.deleteSession(sessionId);
        } catch (RuntimeException re) {
            new RuntimeException("error when deleting session", re);
        }
    }
}
