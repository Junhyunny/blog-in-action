package blog.in.action.proxy.protection;

import blog.in.action.proxy.Client;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProtectionRealSubject implements ProtectionSubject {

    @Override
    public void printNormalThing(Client client) {
        log.info(client.getName() + "님이 출력하였습니다. 접근권한: " + client.getAuthority());
    }

    @Override
    public void printAdminThing(Client client) {
        log.info(client.getName() + "님이 출력하였습니다. 접근권한: " + client.getAuthority());
    }
}
