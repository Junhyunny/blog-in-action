package blog.in.action.entity;

import blog.in.action.converter.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_MEMBER")
public class Member {

    @Id
    private String id;

    @Column
    private String password;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> authorities;
}
