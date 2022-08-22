package blog.in.action.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_MEMBER")
public class Member {

    @Id
    private String id;

    private String name;

    private String email;

    private String address;
}
