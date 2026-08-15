package action.in.blog.todo.controller;

import action.in.blog.todo.repository.TodoRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/legacy")
public class LegacyTodoExportController {

    private final TodoRepository repository;

    public LegacyTodoExportController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportLegacy() {
        var csvHeader = "id,title,description,completed\n";
        var csvContent = repository.findAll()
                .stream()
                .map(todo ->
                        List.of(
                                todo.getId().toString(),
                                todo.getTitle(),
                                todo.getDescription(),
                                todo.isCompleted() ? "완료" : "미완료"
                        )
                )
                .map(columns -> String.join(",", columns))
                .collect(Collectors.joining("\n"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"todos.csv\"")
                .body((csvHeader + csvContent + "\uFEFF").getBytes(StandardCharsets.UTF_8));
    }
}
