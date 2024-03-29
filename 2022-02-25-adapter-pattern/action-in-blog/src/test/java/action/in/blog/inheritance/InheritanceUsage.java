package action.in.blog.inheritance;

import action.in.blog.SessionHandler;

public class InheritanceUsage {

    public static void main(String[] args) {

        // legacy
        // SessionHandler sessionHandler = new SessionHandler(new JdbcSessionRegistry());

        // new
        ClientRegistryAdapter adapter = new ClientRegistryAdapter();
        SessionHandler sessionHandler = new SessionHandler(adapter);

        sessionHandler.getSession("J12345");
    }
}
