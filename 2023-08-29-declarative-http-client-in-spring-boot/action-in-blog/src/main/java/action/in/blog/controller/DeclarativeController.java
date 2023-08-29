package action.in.blog.controller;

import action.in.blog.client.JsonPlaceholderClient;
import action.in.blog.client.PokemonClient;
import action.in.blog.domain.PokemonPage;
import action.in.blog.domain.Todo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class DeclarativeController {

    private final JsonPlaceholderClient jsonPlaceholderClient;
    private final PokemonClient pokemonClient;

    public DeclarativeController(
            JsonPlaceholderClient jsonPlaceholderClient,
            PokemonClient pokemonClient
    ) {
        this.jsonPlaceholderClient = jsonPlaceholderClient;
        this.pokemonClient = pokemonClient;
    }

    @GetMapping("/sync/todos")
    public List<Todo> todosSync(@RequestParam int page, @RequestParam int limit) {
        return jsonPlaceholderClient.todos(page, limit);
    }

    @GetMapping("/sync/pokemon")
    public PokemonPage pokemonSync(@RequestParam int offset, @RequestParam int limit) {
        return pokemonClient.pokemon(offset, limit);
    }

    @GetMapping("/async/todos")
    public Flux<Todo> todosAsync(@RequestParam int page, @RequestParam int limit) {
        return jsonPlaceholderClient.todosAsync(page, limit);
    }

    @GetMapping("/async/pokemon")
    public Mono<PokemonPage> pokemonAsync(@RequestParam int offset, @RequestParam int limit) {
        return pokemonClient.pokemonAsync(offset, limit);
    }
}
