package action.in.blog;

public class JdbcSessionRegistry implements SessionRegistry {

    @Override
    public Object getSession(String sessionId) {
        System.out.println("select s from tb_session s where session_id = " + sessionId);
        return new Object();
    }

    @Override
    public void putSession(String sessionId, Object session) {
        System.out.println(
                " insert into tb_session " +
                "   (session_id, session) " +
                " values " +
                "   (" + sessionId + ", " + session + " )" +
                " on duplicate key update " +
                "   session=" + session
        );
    }

    @Override
    public void deleteSession(String sessionId) {
        System.out.println("delete from tb_session s where session_id = " + sessionId);
    }
}
