package action.in.blog.inheritance;

public class InheritanceUsage {

    public static void main(String[] args) {
        // Client client = new Client(new OldTargetImplementation());
        Client client = new Client(new Adapter());
        client.requestSomething();
    }
}
