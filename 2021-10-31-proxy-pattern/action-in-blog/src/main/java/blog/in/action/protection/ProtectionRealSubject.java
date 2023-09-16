package blog.in.action.protection;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProtectionRealSubject implements ProtectionSubject {

    @Override
    public void printForNormal(User user) {
        log.info("something to print for {}", user);
    }

    @Override
    public void printForAdmin(User user) {
        log.info("something to print for {}", user);
    }
}
