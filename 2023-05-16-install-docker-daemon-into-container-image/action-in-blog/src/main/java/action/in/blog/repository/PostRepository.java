package action.in.blog.repository;

import action.in.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from post p where p.title like trim(:title)", nativeQuery = true)
    List<Post> findByTitleContainsWithTrim(@Param("title") String title);
}

