package daggerok.rest;

import daggerok.domain.Message;
import daggerok.domain.MessageRepository;
import daggerok.pubsub.MessageEmitter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/publish/message")
public class MessagePublisher {

  @NonNull final MessageEmitter emitter;
  @NonNull final MessageRepository messageRepository;

  @PostMapping
  public Mono<Void> postMessage(@Validated @RequestBody(required = false) Message message) {

    emitter.emit(message);
    return messageRepository.save(message)
                            .then();
  }
}
