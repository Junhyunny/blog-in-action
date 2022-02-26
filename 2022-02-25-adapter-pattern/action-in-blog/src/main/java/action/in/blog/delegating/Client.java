package action.in.blog.delegating;

public class Client {

    private final TargetInterface targetInterface;

    public Client(TargetInterface targetInterface) {
        this.targetInterface = targetInterface;
    }

    public void requestSomething() {
        targetInterface.doThing();
    }
}
