package blog.in.action.transcation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    private String id;

    @Column
    private String value;

    public Orders(String id) {
        this.id = id;
    }
}
