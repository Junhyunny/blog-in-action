package blog.in.action.proxy.virtual;

import java.util.concurrent.CompletableFuture;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class VirtualRealSubject implements VirtualSubject {

    private boolean firstItemLoadedFlag;

    private boolean secondItemLoadedFlag;

    private void loadFirstItem() {
        try {
            Thread.sleep(1000);
            firstItemLoadedFlag = true;
            log.info("first item is loaded.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadSecondItem() {
        try {
            Thread.sleep(3000);
            secondItemLoadedFlag = true;
            log.info("second item is loaded.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public VirtualRealSubject() {
        CompletableFuture.runAsync(this::loadFirstItem);
        CompletableFuture.runAsync(this::loadSecondItem);
    }

    public boolean isReady() {
        return firstItemLoadedFlag && secondItemLoadedFlag;
    }

    @Override
    public void print() {
        if (!isReady()) {
            throw new RuntimeException("not ready to print");
        }
        log.info("print something");
    }
}
