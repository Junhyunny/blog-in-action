package blog.in.action.protection;

import static blog.in.action.protection.Authority.ADMIN;
import static blog.in.action.protection.Authority.NORMAL;

public class ProtectionProxy implements ProtectionSubject {

    private final ProtectionSubject subject;

    public ProtectionProxy() {
        this.subject = new ProtectionRealSubject();
    }

    @Override
    public void printForNormal(User user) {
        if (!NORMAL.accessible(user.authority())) {
            throw new RuntimeException("일반 등급 이상만 접근할 수 있습니다.");
        }
        subject.printForNormal(user);
    }

    @Override
    public void printForAdmin(User user) {
        if (!ADMIN.accessible(user.authority())) {
            throw new RuntimeException("관리자 등급 이상만 접근할 수 있습니다.");
        }
        subject.printForAdmin(user);
    }
}
