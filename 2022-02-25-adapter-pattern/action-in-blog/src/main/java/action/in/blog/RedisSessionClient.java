package action.in.blog;

public class RedisSessionClient {

    public Object get(String sessionId) {
        System.out.println("find session by session_id(" + sessionId + ") from redis");
        return new Object();
    }

    public void post(String sessionId, Object session) {
        System.out.println("post session info(" +
                session +
                ") with session_id(" +
                sessionId +
                ") into redis");
    }

    public void delete(String sessionId) {
        System.out.println("delete session by session_id(" + sessionId + ") from redis");
    }
}
