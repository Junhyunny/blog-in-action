package blog.in.action.hello;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_MEMBER", catalog = "hello")
public class Member {

    @Id
    private String id;
    private String nickName;
}
