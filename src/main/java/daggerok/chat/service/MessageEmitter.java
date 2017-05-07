package daggerok.chat.service;

import daggerok.chat.domain.Message;
import daggerok.chat.sse.pubsub.SseClientEmitter;
import daggerok.chat.webflux.pubsub.WebfluxClientEmitter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageEmitter {

  @NonNull final SseClientEmitter sseClientEmitter;
  @NonNull final WebfluxClientEmitter webfluxClientEmitter;

  @Async
  public void process(final Message message) {

    sseClientEmitter.onNext(message);
    webfluxClientEmitter.onNext(message);
  }
}
