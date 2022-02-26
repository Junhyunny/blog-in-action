package action.in.blog.delegating;

public class OldTargetImplementation implements TargetInterface {

    @Override
    public void doThing() {
        System.out.println("do old thing");
    }
}
