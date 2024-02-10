package blog.in.action.controller;

import blog.in.action.domain.Channel;
import blog.in.action.domain.EventLog;
import blog.in.action.publisher.EventLogPublisher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private static final AtomicLong key = new AtomicLong(0);
    private static final Map<Long, String> data = new ConcurrentHashMap<>();

    static {
        data.put(key.incrementAndGet(), "Hello");
        data.put(key.incrementAndGet(), "Redis");
        data.put(key.incrementAndGet(), "World");
    }

    private final EventLogPublisher eventLogPublisher;

    public TodoController(EventLogPublisher eventLogPublisher) {
        this.eventLogPublisher = eventLogPublisher;
    }

    @GetMapping
    public List<Map.Entry<Long, String>> todos(HttpServletRequest request) {
        eventLogPublisher.publish(Channel.TODO_READ, new EventLog(request.getRemoteAddr()));
        return data.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .toList();
    }

    @PostMapping
    public void todos(HttpServletRequest request, @RequestBody String requestBody) {
        eventLogPublisher.publish(Channel.TODO_INSERT, new EventLog(request.getRemoteAddr()));
        data.put(key.incrementAndGet(), requestBody);
    }

    @PutMapping("/{id}")
    public void todos(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody String requestBody
    ) {
        eventLogPublisher.publish(Channel.TODO_UPDATE, new EventLog(request.getRemoteAddr()));
        data.put(id, requestBody);
    }

    @DeleteMapping("/{id}")
    public void todos(HttpServletRequest request, @PathVariable Long id) {
        eventLogPublisher.publish(Channel.TODO_DELETE, new EventLog(request.getRemoteAddr()));
        data.remove(id);
    }
}
