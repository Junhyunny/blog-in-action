package blog.in.action.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Entity
public class ChildEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String state;
    @OneToOne
    private ParentEntity parentEntity;

    public void update() {
        state = "UPDATED";
    }
}
