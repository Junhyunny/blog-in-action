package action.in.blog.domain.transport;

public abstract class Transport {

    protected boolean reserved;

    public boolean isReserved() {
        return reserved;
    }

    public abstract void reserve();
}
