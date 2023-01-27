package blog.in.action.entity;

import blog.in.action.converter.StringListConverter;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_MEMBER")
public class Member {

    @Id
    private String id;
    @Setter
    private String name;
    @Convert(converter = StringListConverter.class)
    private List<String> contacts;

    public void appendContact(String contact) {
        if (contacts == null) {
            this.contacts = new ArrayList<>();
        }
        this.contacts.add(contact);
    }
}
