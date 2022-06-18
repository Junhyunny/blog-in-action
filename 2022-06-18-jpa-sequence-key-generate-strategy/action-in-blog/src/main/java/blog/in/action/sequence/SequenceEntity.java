package blog.in.action.sequence;

import lombok.Getter;

import javax.persistence.*;

@Getter
@SequenceGenerator(
        name = "SEQ_GENERATOR",
        sequenceName = "MY_SEQ",
        allocationSize = 1
)
@Entity
public class SequenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    private Long id;
}
