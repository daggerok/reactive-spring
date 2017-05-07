package daggerok.chat.sse.pubsub;

import daggerok.chat.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseClientEmitter {

  @Qualifier("chatSubscribersContainer")
  final List<SseEmitter> chatSubscribersContainer;

  public SseEmitter createSubscription() {

    val clientSubscription = new SseEmitter();

    chatSubscribersContainer.add(clientSubscription);
    clientSubscription.onCompletion(() -> chatSubscribersContainer.remove(clientSubscription));
    return clientSubscription;
  }

  @Async
  public void onNext(Message message) {

    chatSubscribersContainer.forEach(emitter -> {
      try {

        emitter.send(SseEmitter
                         .event()
                         .name("chat-message-event")
                         .data(message));

      } catch (IOException e) {

        val type = e.getClass().getSimpleName();

        if (e.getMessage().contains("Broken pipe")) {

          log.info("cleaning dead client emitter: {}: {}", type, e.getMessage());

        } else {

          log.error("caused error: {}: {}", type, e.getMessage(), e);
        }
      }
    });
  }
}
