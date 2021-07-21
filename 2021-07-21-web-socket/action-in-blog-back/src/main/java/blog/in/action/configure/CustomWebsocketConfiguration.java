package blog.in.action.configure;

import blog.in.action.component.WebSocketComponent;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class CustomWebsocketConfiguration implements WebSocketConfigurer {

    private final WebSocketComponent webSocketComponent;

    public CustomWebsocketConfiguration(WebSocketComponent webSocketComponent) {
        this.webSocketComponent = webSocketComponent;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketComponent, "/chat")
            .setAllowedOrigins("*")
            .withSockJS()
            .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js");
    }
}
