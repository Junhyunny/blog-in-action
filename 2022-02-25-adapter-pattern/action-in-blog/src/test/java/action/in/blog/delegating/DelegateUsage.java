package action.in.blog.delegating;

public class DelegateUsage {

    public static void main(String[] args) {
        // Client client = new Client(new OldTargetImplementation());
        Adapter adapter = new Adapter(new Adaptee());
        Client client = new Client(adapter);
        client.requestSomething();
    }
}
