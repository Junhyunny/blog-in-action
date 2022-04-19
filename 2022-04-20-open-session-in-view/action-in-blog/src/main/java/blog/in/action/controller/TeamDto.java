package blog.in.action.controller;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDto {

    private long id;
    private String teamName;
}
