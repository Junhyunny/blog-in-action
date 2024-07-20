package blog.in.action.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping("/messages")
public class DeferredResultController {

    private final Map<String, Collection<String>> messagesMap = new ConcurrentHashMap<>();
    private final Map<String, DeferredResult<Collection<String>>> deferredResponsesMap = new ConcurrentHashMap<>();

    @GetMapping("/{id}")
    public DeferredResult<Collection<String>> getMessages(
            @PathVariable String id
    ) {
        DeferredResult<Collection<String>> deferredResult = new DeferredResult<>();
        deferredResult.onTimeout(() -> deferredResponsesMap.remove(id));
        deferredResult.onCompletion(() -> deferredResponsesMap.remove(id));
        deferredResult.onError((throwable -> deferredResponsesMap.remove(id)));
        deferredResponsesMap.put(id, deferredResult);
        return deferredResult;
    }

    @PostMapping("/{id}")
    public void receiveMessage(
            @PathVariable String id,
            @RequestParam String message
    ) {
        Collection<String> messages = messagesMap.getOrDefault(id, new ConcurrentLinkedQueue<>());
        synchronized (messages) {
            messages.add(message);
        }
        messagesMap.put(id, messages);
        if (deferredResponsesMap.containsKey(id)) {
            deferredResponsesMap
                    .get(id)
                    .setResult(messages);
        }
    }
}