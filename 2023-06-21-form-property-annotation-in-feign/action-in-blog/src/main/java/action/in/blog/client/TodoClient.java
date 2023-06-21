package action.in.blog.client;

import action.in.blog.domain.Todo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "todo-client", url = "${todo-service.url}")
public
interface TodoClient {

    @PostMapping(
            value = "/todo",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    long createTodo(@RequestBody Todo todo);
}