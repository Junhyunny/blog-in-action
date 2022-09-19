package action.in.blog.controller;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TodoControllerTests {

    void addTodos(TodoController sut, int size) {
        for (int index = 0; index < size; index++) {
            sut.addTodo(String.valueOf(index));
        }
    }

    @Test
    void check_synchronization_todo_list() {
        String filePath = System.getProperty("user.dir") + "/files/sync";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        TodoController sut = new TodoController(filePath);


        List<CompletableFuture> futures = new ArrayList<>();
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));
        futures.add(CompletableFuture.runAsync(() -> addTodos(sut, 1000)));


        futures.stream().forEach(future -> future.join());
        List<String> todos = TodoUtil.readTodoList(filePath);
        assertThat(todos.size(), equalTo(15000));
    }
}
