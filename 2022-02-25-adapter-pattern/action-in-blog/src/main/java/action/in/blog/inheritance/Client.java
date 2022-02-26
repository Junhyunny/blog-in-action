package action.in.blog.inheritance;

public class Client {

    private final TargetInterface targetInterface;

    public Client(TargetInterface targetInterface) {
        this.targetInterface = targetInterface;
    }

    public void requestSomething() {
        targetInterface.doThing();
    }
}
