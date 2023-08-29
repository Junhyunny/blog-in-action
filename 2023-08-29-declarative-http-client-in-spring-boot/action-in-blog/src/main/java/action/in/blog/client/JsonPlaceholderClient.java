package action.in.blog.client;

import action.in.blog.domain.Todo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Flux;

import java.util.List;

public interface JsonPlaceholderClient {

    @GetExchange("/todos")
    List<Todo> todos(@RequestParam("_page") int page, @RequestParam("_limit") int limit);

    @GetExchange("/todos")
    Flux<Todo> todosAsync(@RequestParam("_page") int page, @RequestParam("_limit") int limit);
}
