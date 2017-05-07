package daggerok.rest;

import daggerok.domain.Message;
import daggerok.domain.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class MessagesResource {

  final MessageRepository messageRepository;

  @GetMapping("/api/v1/messages")
  public Flux<Message> getLastMessages(@RequestParam(required = false, defaultValue = "10", name = "last") Long last) {
    return messageRepository.findAllByOrderByCreatedAtDesc().take(last);
  }
}
