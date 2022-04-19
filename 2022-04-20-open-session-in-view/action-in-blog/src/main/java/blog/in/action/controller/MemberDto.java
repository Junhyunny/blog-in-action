package blog.in.action.controller;

import blog.in.action.domain.Member;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private String id;
    private String memberName;
    private String memberEmail;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .memberName(memberName)
                .memberEmail(memberEmail)
                .build();
    }
}
