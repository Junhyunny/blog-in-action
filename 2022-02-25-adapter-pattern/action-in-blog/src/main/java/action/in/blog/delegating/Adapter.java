package action.in.blog.delegating;

public class Adapter implements TargetInterface {

    private final Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void doThing() {
        // delegate doing new thing to adaptee
        adaptee.doNewThing();
    }
}
