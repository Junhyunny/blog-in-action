package blog.in.action.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_MEMBER")
public class Member {

    public Member(String id) {
        this.id = id;
    }

    @Id
    private String id;

    @Column
    private String password;

    @Column
    private String memberName;

    @Column
    private String memberEmail;
}
