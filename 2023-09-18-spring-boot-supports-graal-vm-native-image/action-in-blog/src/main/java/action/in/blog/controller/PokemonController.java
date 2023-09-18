package action.in.blog.controller;

import action.in.blog.domain.PokemonResponse;
import action.in.blog.proxy.PokemonProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PokemonController {

    private final PokemonProxy pokemonProxy;

    public PokemonController(PokemonProxy pokemonProxy) {
        this.pokemonProxy = pokemonProxy;
    }

    @GetMapping("/pokemons")
    public PokemonResponse getPokemons() {
        return pokemonProxy.getPokemons();
    }
}
