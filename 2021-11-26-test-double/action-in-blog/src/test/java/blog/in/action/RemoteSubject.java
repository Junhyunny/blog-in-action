package blog.in.action;

public interface RemoteSubject {

    void saveOrder(Order order, AUTHORITY authority);

    Order findByOrderId(long id);
}
