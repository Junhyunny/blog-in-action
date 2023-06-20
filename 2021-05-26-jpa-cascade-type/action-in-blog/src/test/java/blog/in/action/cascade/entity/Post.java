package blog.in.action.cascade.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String content;

    @OneToMany(cascade = {CascadeType.REFRESH})
    private List<Comment> comments;
}
