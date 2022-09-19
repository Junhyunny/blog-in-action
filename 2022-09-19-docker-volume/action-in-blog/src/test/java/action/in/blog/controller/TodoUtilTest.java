package action.in.blog.controller;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;

public class TodoUtilTest {

    @Test
    void read_todo_list_from_file() {
        String path = System.getProperty("user.dir");
        String filePath = "/files/todos";


        List<String> todoList = TodoUtil.readTodoList(path + filePath);
        assertThat(todoList.get(0), equalTo("first todo"));
    }

    @Test
    void read_empty_list_from_not_existed_file() {
        String path = System.getProperty("user.dir");
        String filePath = "/files/empty";


        List<String> todoList = TodoUtil.readTodoList(path + "/" + filePath);
        assertThat(todoList, empty());
    }

    @Test
    void write_todo_list_to_not_existed_file() {
        String path = System.getProperty("user.dir");
        String filePath = "/files/new";
        filePath = path + filePath;

        List<String> todoList = new ArrayList<>();
        todoList.add("first todo");
        todoList.add("second todo");


        TodoUtil.writeTodoList(filePath, todoList);


        List<String> result = TodoUtil.readTodoList(filePath);
        assertThat(result.get(0), equalTo("first todo"));
        assertThat(result.get(1), equalTo("second todo"));
    }
}
