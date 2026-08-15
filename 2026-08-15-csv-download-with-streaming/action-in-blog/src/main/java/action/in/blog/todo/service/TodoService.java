package action.in.blog.todo.service;

import action.in.blog.todo.repository.TodoRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final EntityManager entityManager;

    public TodoService(TodoRepository todoRepository, EntityManager entityManager) {
        this.todoRepository = todoRepository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public void exportCsv(OutputStream outputStream) {
        try (var stream = todoRepository.findAllAsStream()) {
            AtomicInteger count = new AtomicInteger();
            var csvHeader = "id,title,description,completed\n";
            outputStream.write(csvHeader.getBytes(StandardCharsets.UTF_8));
            stream.forEach(todo -> {
                var row = String.join(",",
                        List.of(
                                todo.getId().toString(),
                                todo.getTitle(),
                                todo.getDescription(),
                                todo.isCompleted() ? "완료" : "미완료",
                                "\n"
                        )
                );
                count.getAndIncrement();
                try {
                    outputStream.write(row.getBytes(StandardCharsets.UTF_8));
                    if (count.get() % 1000 == 0) {
                        outputStream.flush();
                        entityManager.clear();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            outputStream.write("\uFEFF".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
