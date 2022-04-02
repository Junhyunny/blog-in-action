package action.in.blog.controller;

import action.in.blog.proxy.PokemonProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static action.in.blog.domain.PokemonResponse.Pokemon;

@RestController
public class PokeomonController {

    private final PokemonProxy pokemonProxy;

    public PokeomonController(PokemonProxy pokemonProxy) {
        this.pokemonProxy = pokemonProxy;
    }

    @GetMapping("/pokemons")
    public List<Pokemon> getPokemons() {
        return pokemonProxy.getPokemons().getResults();
    }
}
