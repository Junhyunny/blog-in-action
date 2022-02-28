package action.in.blog.delegate;

import action.in.blog.RedisSessionClient;
import action.in.blog.SessionRegistry;

public class ClientRegistryAdapter implements SessionRegistry {

    private final RedisSessionClient redisSessionClient;

    public ClientRegistryAdapter(RedisSessionClient redisSessionClient) {
        this.redisSessionClient = redisSessionClient;
    }

    @Override
    public Object getSession(String sessionId) {
        return redisSessionClient.get(sessionId);
    }

    @Override
    public void putSession(String sessionId, Object session) {
        redisSessionClient.post(sessionId, session);
    }

    @Override
    public void deleteSession(String sessionId) {
        redisSessionClient.delete(sessionId);
    }
}
