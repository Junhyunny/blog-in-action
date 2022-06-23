package blog.in.action.testdouble;

import blog.in.action.Order;
import blog.in.action.RequestDelegator;

import java.util.HashMap;
import java.util.Map;

public class FakeDelegator implements RequestDelegator {

    private Map<Long, Order> inMemoryDB = new HashMap<>();

    @Override
    public void saveOrder(Order order) {
        inMemoryDB.put(order.getId(), order);
    }

    @Override
    public Order findByOrderId(long id) {
        return inMemoryDB.get(id);
    }
}
