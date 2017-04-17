package daggerok.chat;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/v1")
public class MessageCommandResource {

  List<SseEmitter> sseEmitters = new CopyOnWriteArrayList<>();

  @GetMapping("/chat-messages")
  SseEmitter sseEmitter() {
    val emitter = new SseEmitter();

    sseEmitters.add(emitter);
    emitter.onCompletion(() -> sseEmitters.remove(emitter));
    return emitter;
  }

  @SneakyThrows
  @PostMapping("/send-message")
  void postMessage(@RequestBody String message) {

    for (SseEmitter emitter : sseEmitters) {

      emitter.send(SseEmitter
          .event()
          .name("chat-message-event")
          .data(message));
    }
  }
}
