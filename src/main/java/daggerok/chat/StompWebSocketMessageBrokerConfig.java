package daggerok.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketMessageBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/stomp-chat-endpoint").withSockJS()
        .setHeartbeatTime(120000)
        .setDisconnectDelay(30000)
        .setHttpMessageCacheSize(2000)
        .setStreamBytesLimit(1024 * 1024)
        .setClientLibraryUrl("/webjars/sockjs-client/sockjs.min.js");
  }
}
