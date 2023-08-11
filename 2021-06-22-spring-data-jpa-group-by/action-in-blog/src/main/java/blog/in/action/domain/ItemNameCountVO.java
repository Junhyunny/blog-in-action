package blog.in.action.domain;

import lombok.*;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemNameCountVO {
    private long aCount;
    private long bCount;
    private long cCount;
    private long dCount;
}
