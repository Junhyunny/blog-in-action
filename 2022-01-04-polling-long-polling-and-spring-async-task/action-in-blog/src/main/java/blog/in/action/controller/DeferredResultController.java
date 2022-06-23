package blog.in.action.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class DeferredResultController {

    private Map<String, DeferredResult<Boolean>> secondAuthRequests = new ConcurrentHashMap<>();

    @GetMapping("/pool-size")
    public int getPoolSize() {
        return secondAuthRequests.size();
    }

    @GetMapping("/authentication")
    public DeferredResult<Boolean> requestAuthentication(@RequestParam("userName") String userName) {
        DeferredResult<Boolean> deferredResult = new DeferredResult<>();
        deferredResult.onTimeout(() -> {
            secondAuthRequests.remove(userName);
        });
        deferredResult.onCompletion(() -> {
            secondAuthRequests.remove(userName);
        });
        deferredResult.onError((throwable -> {
            deferredResult.setErrorResult(false);
            secondAuthRequests.remove(userName);
        }));
        secondAuthRequests.put(userName, deferredResult);
        return deferredResult;
    }

    @PostMapping("/authentication")
    public void authenticate(@RequestParam("userName") String userName) {
        DeferredResult secondAuthRequest = secondAuthRequests.remove(userName);
        if (secondAuthRequest == null) {
            return;
        }
        secondAuthRequest.setResult(true);
        return;
    }
}