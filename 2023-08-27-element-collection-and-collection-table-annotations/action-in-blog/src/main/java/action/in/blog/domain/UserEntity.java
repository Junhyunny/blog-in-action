package action.in.blog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_USER")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ElementCollection
    @CollectionTable(
            name = "TB_FAVORITE_POSTS",
            joinColumns = {@JoinColumn(name = "user_id")},
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"user_id", "post_id"})
            }
    )
    private List<FavoritePost> favoritePosts;

    public void addFavoritePosts(List<FavoritePost> favoritePosts) {
        if (this.favoritePosts == null) {
            this.favoritePosts = new ArrayList<>();
        }
        this.favoritePosts.addAll(favoritePosts);
    }

    public void removeFavoritePosts(List<FavoritePost> favoritePosts) {
        final var postIds = favoritePosts.stream()
                .map(FavoritePost::getPostId)
                .collect(Collectors.toSet());
        this.favoritePosts.removeIf(favoritePost -> postIds.contains(favoritePost.getPostId()));
    }

    public void updateFavoritePost(FavoritePost favoritePost) {
        this.favoritePosts.stream()
                .filter(item -> item.getPostId() == favoritePost.getPostId())
                .forEach(item -> item.setRemark(favoritePost.getRemark()));
    }
}
