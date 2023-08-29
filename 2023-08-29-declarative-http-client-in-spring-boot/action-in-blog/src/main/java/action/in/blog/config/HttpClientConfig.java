package action.in.blog.config;

import action.in.blog.client.JsonPlaceholderClient;
import action.in.blog.client.PokemonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {

    private final ExternalUrlConfig externalUrlConfig;

    public HttpClientConfig(ExternalUrlConfig externalUrlConfig) {
        this.externalUrlConfig = externalUrlConfig;
    }

    @Bean
    public PokemonClient pokemonClient(WebClient.Builder builder) {
        var client = builder.baseUrl(externalUrlConfig.pokemonUrl())
                .build();
        var factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .build();
        return factory.createClient(PokemonClient.class);
    }

    @Bean
    public JsonPlaceholderClient jsonPlaceholderClient(WebClient.Builder builder) {
        var client = builder.baseUrl(externalUrlConfig.jsonPlaceholderUrl())
                .build();
        var factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .build();
        return factory.createClient(JsonPlaceholderClient.class);
    }
}
