package blog.in.action.proxy;

import static blog.in.action.proxy.protection.AUTHORITY.NORMAL;
import blog.in.action.proxy.protection.AUTHORITY;
import blog.in.action.proxy.protection.ProtectionProxy;
import blog.in.action.proxy.protection.ProtectionSubject;
import blog.in.action.proxy.remote.RemoteProxy;
import blog.in.action.proxy.remote.RemoteSubject;
import blog.in.action.proxy.virtual.VirtualProxy;
import blog.in.action.proxy.virtual.VirtualSubject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Setter
public class Client {

    private final String name = "Junhyunny";

    private final AUTHORITY authority = NORMAL;

    public void printNormalThingByProtectionProxy() {
        ProtectionSubject subject = new ProtectionProxy();
        subject.printNormalThing(this);
    }

    public void printAdminThingByProtectionProxy() {
        ProtectionSubject subject = new ProtectionProxy();
        subject.printAdminThing(this);
    }

    public void printGoogleByRemoteProxy() {
        RemoteSubject subject = new RemoteProxy();
        subject.printGoogleMainPage();
    }

    public void printByVirtualProxy() throws InterruptedException {
        VirtualSubject subject = new VirtualProxy();
        subject.print();
    }

    public void printByVirtualProxyAfterCountFive() throws InterruptedException {
        VirtualSubject subject = new VirtualProxy();
        for (int index = 1; index <= 5; index++) {
            Thread.sleep(1000);
            log.info(index);
        }
        subject.print();
    }
}
