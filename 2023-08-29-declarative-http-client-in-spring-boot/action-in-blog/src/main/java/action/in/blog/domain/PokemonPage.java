package action.in.blog.domain;

import java.util.List;

public record PokemonPage(
        long count,
        String next,
        String previous,
        List<Pokemon> results
) {
}
