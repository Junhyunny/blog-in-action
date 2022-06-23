package blog.in.action.proxy.protection;

import static blog.in.action.proxy.protection.AUTHORITY.ADMIN;
import static blog.in.action.proxy.protection.AUTHORITY.NORMAL;
import blog.in.action.proxy.Client;

public class ProtectionProxy implements ProtectionSubject {

    private ProtectionRealSubject realSubject;

    public ProtectionProxy() {
        realSubject = new ProtectionRealSubject();
    }

    @Override
    public void printNormalThing(Client client) {
        if (!NORMAL.accessible(client.getAuthority())) {
            throw new RuntimeException("일반 등급 이상만 접근할 수 있습니다.");
        }
        realSubject.printNormalThing(client);
    }

    @Override
    public void printAdminThing(Client client) {
        if (!ADMIN.accessible(client.getAuthority())) {
            throw new RuntimeException("관리자 등급 이상만 접근할 수 있습니다.");
        }
        realSubject.printAdminThing(client);
    }
}
