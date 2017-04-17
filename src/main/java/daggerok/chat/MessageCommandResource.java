package daggerok.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageCommandResource {

  @Qualifier("chatSubscribersContainer")
  final List<SseEmitter> chatSubscribersContainer;

  @PostMapping("/api/v1/command/send-message")
  void postMessage(@RequestBody Message message) {

    chatSubscribersContainer.forEach(emitter -> {
      try {

        emitter.send(SseEmitter
            .event()
            .name("chat-message-event")
            .data(message));

      } catch (IOException e) {

        val type = e.getClass();

        if (type.equals(ClientAbortException.class)) {

          log.info("cleaning dead client emitter: {}: {}", type.getSimpleName(), e.getMessage());

        } else {

          log.error("caused error: {}", e.getMessage(), e);
        }
      }
    });
  }
}
