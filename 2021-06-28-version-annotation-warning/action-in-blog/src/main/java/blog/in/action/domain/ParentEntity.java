package blog.in.action.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String someValue;
    @Setter
    @OneToOne(mappedBy = "parentEntity", cascade = CascadeType.ALL)
    private ChildEntity childEntity;
    @Version
    private Long versionNo = 0L; // error
//    private Long versionNo; // not error
}
