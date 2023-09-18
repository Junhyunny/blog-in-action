package action.in.blog.domain;

import lombok.Builder;

import java.util.List;

public record PokemonResponse(
    int count,
    String next,
    String previous,
    List<Pokemon> results
) {
    @Builder
    public PokemonResponse{}
}
