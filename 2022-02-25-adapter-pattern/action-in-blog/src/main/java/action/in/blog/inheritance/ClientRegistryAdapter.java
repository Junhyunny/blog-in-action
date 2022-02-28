package action.in.blog.inheritance;

import action.in.blog.RedisSessionClient;
import action.in.blog.SessionRegistry;

public class ClientRegistryAdapter extends RedisSessionClient implements SessionRegistry {

    @Override
    public Object getSession(String sessionId) {
        return super.get(sessionId);
    }

    @Override
    public void putSession(String sessionId, Object session) {
        super.post(sessionId, session);
    }

    @Override
    public void deleteSession(String sessionId) {
        super.delete(sessionId);
    }
}
