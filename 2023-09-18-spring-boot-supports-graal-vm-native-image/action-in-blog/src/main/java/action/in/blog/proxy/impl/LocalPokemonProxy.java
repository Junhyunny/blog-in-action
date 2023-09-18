package action.in.blog.proxy.impl;

import action.in.blog.domain.Pokemon;
import action.in.blog.domain.PokemonResponse;
import action.in.blog.proxy.PokemonProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile(value = {"local", "test"})
@Service
public class LocalPokemonProxy implements PokemonProxy {

    @Override
    public PokemonResponse getPokemons() {
        return PokemonResponse.builder()
                .count(3)
                .next("https://pokeapi.co/api/v2/pokemon?offset=10&limit=10")
                .results(
                        List.of(
                                new Pokemon("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
                                new Pokemon("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/"),
                                new Pokemon("venusaur", "https://pokeapi.co/api/v2/pokemon/3/")
                        )
                )
                .build();
    }
}
