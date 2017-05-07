package daggerok.pubsub;

import daggerok.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class MessageEmitter {

  private final EmitterProcessor<ServerSentEvent<Message>> subscription = EmitterProcessor.create();

  @Async
  public void emit(Message message) {
    subscription.onNext(ServerSentEvent.builder(message).build());
  }

  public Flux<ServerSentEvent<Message>> publisher() {
//    return subscription.log();
    return subscription;
  }
}
