package action.in.blog.actioninblog.todo;

import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.stream.Stream;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    @Query("SELECT todo FROM TodoEntity todo")
    @QueryHints(
            value = @QueryHint(name = HibernateHints.HINT_FETCH_SIZE, value = "10")
    )
    Stream<TodoEntity> streamAll();
}
