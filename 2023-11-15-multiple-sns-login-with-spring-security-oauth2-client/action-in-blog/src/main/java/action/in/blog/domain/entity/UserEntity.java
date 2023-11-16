package action.in.blog.domain.entity;

import action.in.blog.domain.enums.LoginType;
import action.in.blog.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_USER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String email;
    private String nickName;
    @Enumerated(value = EnumType.STRING)
    private LoginType loginType;
    @Column(name = "oauth2_client_id")
    private String oauth2ClientId;
}
