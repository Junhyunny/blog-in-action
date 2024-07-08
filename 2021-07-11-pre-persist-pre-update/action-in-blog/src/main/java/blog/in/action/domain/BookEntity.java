package blog.in.action.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BookEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String isbn;
    @Column
    private String title;

    @Override
    @PrePersist
    public void prePersist() {
        if (isbn == null) {
            throw new RuntimeException("isbn must be not null");
        }
        super.prePersist();
    }

    @Override
    @PreUpdate
    public void preUpdate() {
        super.preUpdate();
    }
}
