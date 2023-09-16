package blog.in.action.virtual;

public class VirtualProxy implements VirtualSubject {

    private VirtualRealSubject realSubject;

    @Override
    public void print() {
        if (realSubject == null) {
            this.realSubject = new VirtualRealSubject();
        }
        realSubject.print();
    }
}
