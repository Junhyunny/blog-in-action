package blog.in.action.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT DISTINCT p FROM Post p JOIN FETCH p.replies WHERE p.content LIKE %:content%",
            countQuery = "SELECT COUNT(DISTINCT p) FROM Post p INNER JOIN p.replies WHERE p.content LIKE %:content%")
    Page<Post> findByContentLikeFetchJoin(String content, Pageable pageable);

    @Query(value = "SELECT DISTINCT p FROM Post p INNER JOIN p.replies WHERE p.content LIKE %:content%",
            countQuery = "SELECT COUNT(DISTINCT p) FROM Post p INNER JOIN p.replies WHERE p.content LIKE %:content%")
    Page<Post> findByContentLikeInnerJoin(String content, Pageable pageable);

    @EntityGraph(attributePaths = {"replies"})
    @Query(value = "SELECT p FROM Post p WHERE p.content LIKE %:content%",
            countQuery = "SELECT COUNT(p) FROM Post p WHERE p.content LIKE %:content%")
    Page<Post> findByContentLikeEntityGraph(String content, Pageable pageable);

    @Query(value = "SELECT p FROM Post p WHERE p.content LIKE %:content%",
            countQuery = "SELECT COUNT(p) FROM Post p WHERE p.content LIKE %:content%")
    Page<Post> findByContentLike(String content, Pageable pageable);
}
