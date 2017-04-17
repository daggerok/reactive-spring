package daggerok.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageQueryResource {

  @Qualifier("chatSubscribersContainer")
  final List<SseEmitter> chatSubscribersContainer;

  @GetMapping("/api/v1/query/subscribe/chat-messages")
  SseEmitter subscribe() {
    val clientSubscription = new SseEmitter();

    chatSubscribersContainer.add(clientSubscription);
    clientSubscription.onCompletion(() -> chatSubscribersContainer.remove(clientSubscription));
    return clientSubscription;
  }
}
