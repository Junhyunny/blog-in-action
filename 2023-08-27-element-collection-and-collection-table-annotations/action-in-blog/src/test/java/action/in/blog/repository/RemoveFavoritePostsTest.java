package action.in.blog.repository;

import action.in.blog.Helper;
import action.in.blog.domain.FavoritePost;
import action.in.blog.domain.UserEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(Helper.class)
@DataJpaTest
class RemoveFavoritePostsTest {

    @Autowired
    Helper helper;
    @Autowired
    EntityManager entityManager;
    @Autowired
    UserRepository sut;

    @BeforeEach
    void beforeEach() {
        helper.transaction(
                () -> entityManager
                        .createNativeQuery("delete from tb_favorite_posts")
                        .executeUpdate()
        );
    }

    void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    private FavoritePost createFavoritePost(long postId, String remark) {
        return FavoritePost.builder()
                .postId(postId)
                .remark(remark)
                .build();
    }

    @Test
    void removeFavoritePosts() {

        helper.transaction(() -> {
            var user = sut.findByName("Junhyunny");
            user.addFavoritePosts(
                    List.of(
                            createFavoritePost(1L, "Hello"),
                            createFavoritePost(2L, "Spring"),
                            createFavoritePost(3L, "World")
                    )
            );
        });


        helper.transaction(() -> {
            var user = sut.findByName("Junhyunny");
            user.removeFavoritePosts(
                    List.of(
                            createFavoritePost(2L, "Spring")
                    )
            );
        });


        flushAndClear();
        var result = entityManager
                .createQuery("select ue from UserEntity ue where ue.name = 'Junhyunny'", UserEntity.class)
                .getSingleResult();
        var favoritePosts = result.getFavoritePosts();
        assertEquals(2, favoritePosts.size());
        assertEquals(1L, favoritePosts.get(0).getPostId());
        assertEquals("Hello", favoritePosts.get(0).getRemark());
        assertEquals(3L, favoritePosts.get(1).getPostId());
        assertEquals("World", favoritePosts.get(1).getRemark());
    }
}