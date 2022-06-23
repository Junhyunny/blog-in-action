package blog.in.action.cascade.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "TB_POST")
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> commentList;
}
