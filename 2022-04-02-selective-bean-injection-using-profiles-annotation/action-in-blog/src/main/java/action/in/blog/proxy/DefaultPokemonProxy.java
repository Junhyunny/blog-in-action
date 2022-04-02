package action.in.blog.proxy;

import action.in.blog.domain.PokemonResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Profile("!local")
@Component
public class DefaultPokemonProxy implements PokemonProxy {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public PokemonResponse getPokemons() {
        return restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon", PokemonResponse.class);
    }
}
