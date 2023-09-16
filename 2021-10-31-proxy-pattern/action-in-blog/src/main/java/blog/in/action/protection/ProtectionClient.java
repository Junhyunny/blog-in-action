package blog.in.action.protection;

public class ProtectionClient {

    private final ProtectionSubject subject;

    public ProtectionClient(ProtectionSubject subject) {
        this.subject = subject;
    }

    public void printForAdmin(User user) {
        subject.printForAdmin(user);
    }

    public void printForNormal(User user) {
        subject.printForNormal(user);
    }
}
