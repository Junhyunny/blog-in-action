package blog.in.action.reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query(value = "SELECT r FROM Reply r JOIN FETCH r.post WHERE r.content LIKE %:content%",
            countQuery = "SELECT COUNT(r) FROM Reply r INNER JOIN r.post WHERE r.content LIKE %:content%")
    Page<Reply> findByContentLikeFetchJoin(String content, Pageable pageable);

    @Query(value = "SELECT r FROM Reply r INNER JOIN r.post WHERE r.content LIKE %:content%",
            countQuery = "SELECT COUNT(r) FROM Reply r INNER JOIN r.post WHERE r.content LIKE %:content%")
    Page<Reply> findByContentLikeInnerJoin(String content, Pageable pageable);

    @EntityGraph(attributePaths = {"post"})
    @Query(value = "SELECT r FROM Reply r WHERE r.content LIKE %:content%",
            countQuery = "SELECT COUNT(r) FROM Reply r LEFT OUTER JOIN r.post WHERE r.content LIKE %:content%")
    Page<Reply> findByContentLikeEntityGraph(String content, Pageable pageable);
}
