package blog.`in`.action.service

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TodoService {

    fun getTodos(): List<String> {
        return listOf("Hello", "World")
    }

    fun getTodo(id: Long): String {
        return "Hello"
    }

    fun getTodosInToday(localDate: LocalDate?): List<String> {
        return listOf("Hello", "World", localDate.toString())
    }

    fun getFilteredTodosInToday(ids: List<Long>, localDate: LocalDate?): List<String> {
        return listOf("Hello", "World", ids.size.toString(), localDate.toString())
    }
}