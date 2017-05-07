package daggerok.chat.webflux.pubsub;

import daggerok.chat.domain.Message;
import daggerok.chat.domain.MessageRepository;
import daggerok.chat.domain.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebfluxSubscribeResource {

  @NonNull final WebfluxClientEmitter webfluxClientEmitter;
  @NonNull final UserRepository userRepository;
  @NonNull final MessageRepository messageRepository;

  @GetMapping(value = "/api/v1/webflux/subscribe/chat", produces = TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<Message>> subscribe() {
    return webfluxClientEmitter.getMessages();
  }
}
