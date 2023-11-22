package action.in.blog.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CardLikeEntity {

    @Id
    private String cardId;
    private int count;

    public CardLikeEntity() {
    }

    public CardLikeEntity(String cardId) {
        this.cardId = cardId;
        this.count = 1;
    }

    public void increase() {
        count++;
    }
}
