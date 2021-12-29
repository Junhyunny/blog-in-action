package blog.in.action.domain.member;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_MEMBER")
public class Member {

    @Id
    private String id;

    @Column
    private String password;

    @Column
    private String memberName;
}
