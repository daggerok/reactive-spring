package daggerok.rest;

import daggerok.domain.Message;
import daggerok.pubsub.MessageEmitter;
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
public class MessageSubscriber {

  @NonNull final MessageEmitter messageEmitter;

  @GetMapping(value = "/api/v1/subscribe/messages", produces = TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<Message>> subscribe() {
    return messageEmitter.publisher();
  }
}
