package action.in.blog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PokemonResponse {

    private int count;
    private String next;
    private String previous;
    private List<Pokemon> results;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Pokemon {
        private String name;
        private String url;
    }
}
