package action.in.blog.delegate;

import action.in.blog.RedisSessionClient;
import action.in.blog.SessionHandler;

public class DelegateUsage {

    public static void main(String[] args) {

        // legacy
        // SessionHandler sessionHandler = new SessionHandler(new JdbcSessionRegistry());

        // new
        RedisSessionClient adaptee = new RedisSessionClient();
        ClientRegistryAdapter adapter = new ClientRegistryAdapter(adaptee);
        SessionHandler sessionHandler = new SessionHandler(adapter);

        sessionHandler.getSession("J12345");
    }
}
