package daggerok.chat.resource;

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

    config.setApplicationDestinationPrefixes("/app");
    config.enableStompBrokerRelay("/topic")
          .setClientLogin("guest")
          .setClientPasscode("guest")
          .setSystemLogin("guest")
          .setSystemPasscode("guest")
          .setSystemHeartbeatSendInterval(290000)
          .setSystemHeartbeatReceiveInterval(290000)
          .setUserRegistryBroadcast("/topic/simp-user-registry");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {

    registry.addEndpoint("/stomp-chat-endpoint")
            .withSockJS()
            .setHeartbeatTime(290000)
            .setDisconnectDelay(30000)
            .setHttpMessageCacheSize(2000)
            .setStreamBytesLimit(1024 * 1024)
            .setClientLibraryUrl("/webjars/sockjs-client/sockjs.min.js")
    ;
  }
}
