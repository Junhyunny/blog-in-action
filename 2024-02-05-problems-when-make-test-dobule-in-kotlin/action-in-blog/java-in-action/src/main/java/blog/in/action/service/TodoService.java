package blog.in.action.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TodoService {

    public List<String> getTodos() {
        return List.of("Hello", "World");
    }

    public String getTodo(long id) {
        return "Hello";
    }

    public List<String> getTodosInToday(LocalDate localDate) {
        return List.of("Hello", "World", localDate.toString());
    }
}
