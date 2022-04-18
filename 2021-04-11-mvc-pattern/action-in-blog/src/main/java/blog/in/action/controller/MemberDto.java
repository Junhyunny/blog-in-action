package blog.in.action.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private String id;
    private String memberName;
    private String memberEmail;
}
