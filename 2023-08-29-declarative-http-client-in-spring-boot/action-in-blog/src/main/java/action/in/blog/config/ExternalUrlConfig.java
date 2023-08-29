package action.in.blog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "external-service")
public record ExternalUrlConfig(
        String jsonPlaceholderUrl,
        String pokemonUrl
) {
    @ConstructorBinding
    public ExternalUrlConfig {
    }
}
