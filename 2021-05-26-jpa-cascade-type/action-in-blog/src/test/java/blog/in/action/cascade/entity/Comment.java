package blog.in.action.cascade.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_COMMENT")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String comment;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "POST_ID")
    private Post post;
}
