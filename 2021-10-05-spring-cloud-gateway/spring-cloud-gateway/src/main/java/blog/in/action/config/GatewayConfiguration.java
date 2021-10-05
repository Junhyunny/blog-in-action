package blog.in.action.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(r -> r.path("/a-service/**")
                .filters(f -> f.rewritePath("/a-service/(?<path>.*)", "/${path}"))
                .uri("http://localhost:8081")
            )
            .route(r -> r.path("/b-service/**")
                .filters(f -> f.rewritePath("/b-service/(?<path>.*)", "/${path}"))
                .uri("http://localhost:8082")
            )
            .build();
    }
}
