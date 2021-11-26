package blog.in.action;

import lombok.extern.log4j.Log4j2;

import static blog.in.action.AUTHORITY.ADMIN;

@Log4j2
public class RemoteProxy implements RemoteSubject {

    private final RequestDelegator requestDelegator;

    public RemoteProxy(RequestDelegator requestDelegator) {
        this.requestDelegator = requestDelegator;
    }

    @Override
    public void saveOrder(Order order, AUTHORITY authority) {
        if (!isAdmin(authority)) {
            throw new RuntimeException("only admin accessible");
        }
        requestDelegator.saveOrder(order);
    }

    @Override
    public Order findByOrderId(long id) {
        return requestDelegator.findByOrderId(id);
    }

    boolean isAdmin(AUTHORITY authority) {
        return ADMIN.accessible(authority);
    }
}
