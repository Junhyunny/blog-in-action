package action.in.blog.todo.controller;

import action.in.blog.todo.service.TodoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
public class TodoExportController {

    private final TodoService todoService;

    public TodoExportController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/export")
    public ResponseEntity<StreamingResponseBody> export() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"todos.csv\"")
                // .body((outputStream) -> todoService.exportCsv(outputStream)); 아래와 동일
                .body(todoService::exportCsv);
    }
}
