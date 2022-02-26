package action.in.blog.inheritance;

public class Adapter extends Adaptee implements TargetInterface {

    @Override
    public void doThing() {
        // do new thing by using method from super class
        super.doNewThing();
    }
}
