package blog.in.action.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_MEMBER")
public class Member {

    public Member(String id) {
        this.id = id;
    }

    @Id
    private String id;

    @Column
    private String memberName;

    @Column
    private String memberEmail;

    @ManyToOne
    private Team team;

    public String getChangedName() {
        this.memberName += "-changed!";
        return this.memberName;
    }

    public String getChangedEmail() {
        this.memberEmail += "-changed!";
        return this.memberEmail;
    }
}
