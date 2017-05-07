package daggerok.chat.sse.pubsub;

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
public class SseSubscribeResource {

  final SseClientEmitter sseClientEmitter;

  @GetMapping("/api/v1/sse/subscribe/chat")
  public SseEmitter subscribe() {
    return sseClientEmitter.createSubscription();
  }
}
