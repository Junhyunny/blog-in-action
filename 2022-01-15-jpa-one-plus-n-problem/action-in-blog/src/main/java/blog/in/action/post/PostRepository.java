package blog.in.action.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitle(String title);

    @Query(value = "SELECT DISTINCT p FROM Post p JOIN FETCH p.replies WHERE p.title = :title")
    List<Post> findDistinctByTitleFetchJoin(String title);

    @Query(value = "SELECT p FROM Post p JOIN FETCH p.replies WHERE p.title = :title")
    Set<Post> findByTitleFetchJoin(String title);

    @EntityGraph(attributePaths = {"replies"})
    @Query(value = "SELECT DISTINCT p FROM Post p WHERE p.title = :title")
    List<Post> findDistinctByTitleEntityGraph(String title);

    @EntityGraph(attributePaths = {"replies"})
    @Query(value = "SELECT p FROM Post p WHERE p.title = :title")
    Set<Post> findByTitleEntityGraph(String title);

    @Query(value = "SELECT p FROM Post p JOIN FETCH p.replies WHERE p.title = :title")
    List<Post> findByTitleFetchJoinWithoutDistinct(String title);

    @EntityGraph(attributePaths = {"replies"})
    @Query(value = "SELECT p FROM Post p WHERE p.title = :title")
    List<Post> findByTitleEntityGraphWithoutDistinct(String title);
}
