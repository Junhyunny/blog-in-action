package blog.in.action.dto;

import blog.in.action.entity.Member;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class MemberDto {

    private String id;
    private List<String> authorities;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .authorities(member.getAuthorities())
                .build();
    }
}
