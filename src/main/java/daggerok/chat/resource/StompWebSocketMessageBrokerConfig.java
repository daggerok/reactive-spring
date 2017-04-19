package daggerok.chat.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketMessageBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {


  @Value("${stomp.relay.host}") String relayHost;
  @Value("${stomp.relay.user}") String relayUser;
  @Value("${stomp.relay.pass}") String relayPass;
  @Value("${stomp.relay.heartbeat}") Long relayHeartbeat;
  @Value("${stomp.endpoint.heartbeat}") Long endpointHeartbeat;
  @Value("${stomp.endpoint.delay}") Long delay;
  @Value("${stomp.endpoint.message-cache-size}") Integer messageCacheSize;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {

    config.setApplicationDestinationPrefixes("/app");
    config.enableStompBrokerRelay("/topic")
          .setRelayHost(relayHost)
          .setClientLogin(relayUser)
          .setClientPasscode(relayPass)
          .setSystemLogin(relayUser)
          .setSystemPasscode(relayPass)
          .setSystemHeartbeatSendInterval(relayHeartbeat)
          .setSystemHeartbeatReceiveInterval(relayHeartbeat)
          .setUserRegistryBroadcast("/topic/simp-user-registry")
    ;
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {

    registry.addEndpoint("/stomp-chat-endpoint")
            .withSockJS()
            .setDisconnectDelay(delay)
            .setStreamBytesLimit(1024 * 1024)
            .setHeartbeatTime(endpointHeartbeat)
            .setHttpMessageCacheSize(messageCacheSize)
            .setClientLibraryUrl("/webjars/sockjs-client/sockjs.min.js")
    ;
  }
}
