package daggerok.chat.webflux.pubsub;

import daggerok.chat.domain.Message;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@Service
public class WebfluxClientEmitter {

  final EmitterProcessor<ServerSentEvent<Message>> emitter = EmitterProcessor.create();

  @Async
  public void onNext(Message message) {

    emitter.onNext(ServerSentEvent.builder(message)
                                  .event("reactive-chat-message-event")
                                  .build());
  }

  public Flux<ServerSentEvent<Message>> getMessages() {
    return emitter.log();
  }
}
