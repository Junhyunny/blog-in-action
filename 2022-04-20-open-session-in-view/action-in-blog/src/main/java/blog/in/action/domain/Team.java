package blog.in.action.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_TEAM")
public class Team {

    public Team(long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String teamName;

    @OneToMany(targetEntity = Member.class, mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public int getMembersCount() {
        return members.size();
    }
}
