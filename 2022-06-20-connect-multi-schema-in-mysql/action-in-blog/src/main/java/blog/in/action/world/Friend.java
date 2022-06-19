package blog.in.action.world;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_FRIEND", catalog = "world")
public class Friend {

    @Id
    private String id;
    private String nickName;
}
