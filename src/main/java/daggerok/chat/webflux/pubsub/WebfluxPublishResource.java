package daggerok.chat.webflux.pubsub;

import daggerok.chat.domain.Message;
import daggerok.chat.domain.MessageRepository;
import daggerok.chat.service.MessageEmitter;
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
@RequestMapping("/api/v1/webflux/publish/message")
public class WebfluxPublishResource {

  @NonNull final MessageRepository messageRepository;
  @NonNull final MessageEmitter messageEmitter;

  @PostMapping
  public Mono<Void> postMessage(@Validated @RequestBody(required = false) Message message) {

    messageEmitter.process(message);
    return messageRepository.save(message)
                            .then();
  }
}
