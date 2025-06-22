package action.in.blog.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    @Column
    private Boolean enable;

    public MemberEntity() {
    }

    public MemberEntity(
            String email,
            Boolean enable
    ) {
        this.email = email;
        this.enable = enable;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEnable() {
        return enable;
    }

    public long getId() {
        return id;
    }
}
