package blog.in.action;

public interface RequestDelegator {

    void saveOrder(Order order);

    Order findByOrderId(long id);
}
