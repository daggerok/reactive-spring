package daggerok.chat.sse.pubsub;

import daggerok.chat.domain.Message;
import daggerok.chat.domain.MessageRepository;
import daggerok.chat.service.MessageEmitter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SsePublishResource {

  @NonNull final MessageEmitter messageEmitter;
  @NonNull final MessageRepository messageRepository;

  @Transactional
  @PostMapping("/api/v1/sse/publish/message")
  public void postMessage(@RequestBody Message message) {

    messageEmitter.process(message);
    messageRepository.save(message);
  }
}
