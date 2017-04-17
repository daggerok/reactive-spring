package daggerok.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
public class SseContainerConfig {

  @Bean(name = "chatSubscribersContainer")
  List<SseEmitter> chatSubscribersContainer() {
    return new CopyOnWriteArrayList<>();
  }
}
