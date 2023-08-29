package action.in.blog.client;

import action.in.blog.domain.PokemonPage;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange(url = "/api/v2")
public interface PokemonClient {

    @GetExchange("/pokemon")
    PokemonPage pokemon(@RequestParam int offset, @RequestParam int limit);

    @GetExchange("/pokemon")
    Mono<PokemonPage> pokemonAsync(@RequestParam int offset, @RequestParam int limit);
}
