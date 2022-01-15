package blog.in.action.reply;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Reply findByContent(String content);

    @Query(value = "SELECT r FROM Reply r JOIN FETCH r.post WHERE r.content = :content")
    Reply findByContentFetchJoin(String content);

    @EntityGraph(attributePaths = {"post"})
    @Query(value = "SELECT r FROM Reply r WHERE r.content = :content")
    Reply findByContentEntityGraph(String content);
}
