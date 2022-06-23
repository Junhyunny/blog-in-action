package blog.in.action.testdouble;

import blog.in.action.Order;
import blog.in.action.RequestDelegator;

import java.util.ArrayList;
import java.util.List;

public class SpyDelegator implements RequestDelegator {

    private int saveOrderCallCnt = 0;

    @Override
    public void saveOrder(Order order) {
        saveOrderCallCnt++;
    }

    @Override
    public Order findByOrderId(long id) {
        return null;
    }

    public int getSaveOrderCallCnt() {
        return saveOrderCallCnt;
    }
}
