package action.in.blog.repository;

import action.in.blog.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByName(String name);

    @Modifying
    @Query(value = """
            delete from tb_favorite_posts 
            where user_id = (select id from tb_user where name = :userName)
              and post_id in :favoritePosts
            """,
            nativeQuery = true)
    void deleteFavoritePosts(String userName, List<Long> favoritePosts);
}
