package blog.in.action;

import blog.in.action.testdouble.*;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static blog.in.action.AUTHORITY.ADMIN;
import static blog.in.action.AUTHORITY.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Log4j2
public class TestDoubleTest {

    @Test
    public void when_giveNormal_then_false() {
        RemoteProxy remoteProxy = new RemoteProxy(new DummyDelegator());
        assertThat(remoteProxy.isAdmin(NORMAL)).isFalse();
    }

    @Test
    public void when_giveAdmin_then_saveOrderCallCntIsOne() {
        SpyDelegator spy = new SpyDelegator();
        RemoteProxy remoteProxy = new RemoteProxy(spy);
        remoteProxy.saveOrder(new Order(0, "order"), ADMIN);
        assertThat(spy.getSaveOrderCallCnt()).isEqualTo(1);
    }

    @Test
    public void when_giveId_then_returnTargetOrder() {
        long id = 1;
        RemoteProxy remoteProxy = new RemoteProxy(new StubDelegator());
        assertThat(remoteProxy.findByOrderId(id).getId()).isEqualTo(id);
    }

    @Test
    public void when_giveIdAfterSaveOrderAsAdmin_then_returnTargetOrder() {
        long id = 1;
        RemoteProxy remoteProxy = new RemoteProxy(new FakeDelegator());
        remoteProxy.saveOrder(new Order(id, null), ADMIN);
        assertThat(remoteProxy.findByOrderId(id).getId()).isEqualTo(id);
    }

    @Test
    public void when_giveIdAfterSaveOrderAsNormal_then_returnNullAndSaveCallCntZero() {
        long id = 1;
        MockDelegator mock = new MockDelegator();
        RemoteProxy remoteProxy = new RemoteProxy(mock);
        assertThrows(RuntimeException.class, () -> remoteProxy.saveOrder(new Order(id, null), NORMAL));
        assertThat(remoteProxy.findByOrderId(id)).isNull();
        assertThat(mock.getSaveOrderCallCnt()).isZero();
    }
}
