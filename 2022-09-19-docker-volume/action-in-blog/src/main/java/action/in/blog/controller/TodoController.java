package action.in.blog.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
public class TodoController {

    private final String filePath;
    private final List<String> todoList;

    public TodoController(@Value("${todo.file.path}") String filePath) {
        log.info(filePath);
        this.filePath = filePath;
        this.todoList = TodoUtil.readTodoList(filePath);
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todoList", todoList);
        return "index";
    }

    @PostMapping("/todo")
    public String addTodo(@RequestParam("todo") String todo) {
        synchronized (todoList) {
            List<String> temp = new ArrayList<>(todoList);
            temp.add(todo);
            TodoUtil.writeTodoList(filePath, temp);
            todoList.add(todo);
        }
        return "redirect:/";
    }
}

class TodoUtil {

    public static List<String> readTodoList(String filePath) {
        List<String> result = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return result;
        }
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void writeTodoList(String filePath, List<String> todoList) {
        File file = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            for (String todo : todoList) {
                bufferedWriter.write(todo);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}