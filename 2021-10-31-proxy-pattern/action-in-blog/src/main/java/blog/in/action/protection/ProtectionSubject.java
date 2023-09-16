package blog.in.action.protection;

public interface ProtectionSubject {

    void printForNormal(User authority);

    void printForAdmin(User authority);
}
