package blog.in.action.protection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProtectionClientTest {

    @Test
    void user_is_admin_when_print() {

        var user = new User("Junhyunny", Authority.ADMIN);
        var sut = new ProtectionClient(new ProtectionProxy());


        sut.printForNormal(user);
        sut.printForAdmin(user);
    }

    @Test
    void user_is_normal_when_print() {

        var user = new User("Junhyunny", Authority.NORMAL);
        var sut = new ProtectionClient(new ProtectionProxy());


        sut.printForNormal(user);
        var throwable = assertThrows(RuntimeException.class, () -> sut.printForAdmin(user));
        assertEquals("관리자 등급 이상만 접근할 수 있습니다.", throwable.getMessage());
    }
}