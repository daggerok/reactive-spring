package daggerok.rest;

import daggerok.domain.Message;
import daggerok.domain.MessageRepository;
import daggerok.rest.events.MessageCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessagesResource {

  final SimpUserRegistry simpUserRegistry;
  final MessageRepository messageRepository;
  final ApplicationEventPublisher applicationEventPublisher;
  final SimpMessageSendingOperations simpMessageSendingOperations;

  private String prefix = "(localhost) ";

  @SneakyThrows
  @PostConstruct
  public void init() {
    prefix = "(" + InetAddress.getLocalHost().getHostAddress() + ") ";
  }

  @GetMapping("/api/v1/users")
  public Set<SimpUser> users() {
    return simpUserRegistry.getUsers();
  }

  @GetMapping("/api/v1/messages")
  public Flux<Message> getLastMessages(@RequestParam(required = false, defaultValue = "10", name = "last") Long last) {
    return messageRepository.findAllByOrderByCreatedAtDesc()
                            .take(last);
  }

  @SendTo("/topic/messages.subscribe")
  @SubscribeMapping("/api/v1/messages")
  public CompletableFuture<List<Message>> getLatestMessages(Long last) {

    return messageRepository.findAllByOrderByCreatedAtDesc()
                            .take(last)
                            .collectList()
                            .toFuture();
  }

  // coupled but with reactive fashion
  @SendTo("/topic/message.subscribe")
  @MessageMapping("/api/v1/message/publish")
  public CompletableFuture<Message> publisherSubscriber(@RequestBody Message message) {

    return Mono.just(message.setBody(prefix + message.getBody()))
               .doOnNext(messageRepository::save)
               .toFuture();
  }

  /**
   * only one subscriber will receive that message
   */

  @MessageMapping("/api/v1/message/winner")
  public void publishMessageToWinner(@RequestBody Message message) {

    applicationEventPublisher.publishEvent(
        new MessageCreatedEvent(message.setBody("got a prize!")));
  }

  @EventListener
  public void handleAndSendWinnerMessage(MessageCreatedEvent event) {

    messageRepository.save(event.getMessage())
                     .subscribe(message -> simpMessageSendingOperations
                         .convertAndSend("/queue/message.winner", message));
  }
}
