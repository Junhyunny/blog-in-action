package blog.in.action.testdouble;

import blog.in.action.Order;
import blog.in.action.RequestDelegator;

public class StubDelegator implements RequestDelegator {

    @Override
    public void saveOrder(Order order) {

    }

    @Override
    public Order findByOrderId(long id) {
        return new Order(id, null);
    }
}
