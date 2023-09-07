package blog.in.action.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String state;
    @Version
    private Long versionNo = 0L; // error
//    private Long versionNo; // ok
//    private long versionNo; // ok

    public ParentEntity() {
        state = "CREATED";
    }
}
