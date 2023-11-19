package action.in.blog.domain;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"cardId", "userId"})})
public class CollectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userId;
    private String cardId;

    public CollectEntity() {
    }

    public CollectEntity(String userId, String cardId) {
        this.userId = userId;
        this.cardId = cardId;
    }
}
