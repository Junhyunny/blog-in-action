package action.in.blog.todo.repository;

import action.in.blog.todo.domain.TodoEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.stream.Stream;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    @Query("select t from TodoEntity t")
    @QueryHints(value = @QueryHint(name = "org.hibernate.fetchSize", value = "1000"))
    Stream<TodoEntity> findAllAsStream();
}
