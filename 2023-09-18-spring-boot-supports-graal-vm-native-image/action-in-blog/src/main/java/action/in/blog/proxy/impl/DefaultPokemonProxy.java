package action.in.blog.proxy.impl;

import action.in.blog.domain.PokemonResponse;
import action.in.blog.proxy.PokemonProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Profile("!local & !test")
@Service
public class DefaultPokemonProxy implements PokemonProxy {

    private final RestTemplate restTemplate;

    public DefaultPokemonProxy() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public PokemonResponse getPokemons() {
        return restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon", PokemonResponse.class);
    }
}
