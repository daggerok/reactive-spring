package daggerok.chat.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TestCommandResource {

  final SimpMessagingTemplate messagingTemplate;

  /**
   * 1. receive clients {@param payload} to destination: `/api/v1/command/consume-stomp-message`
   * 2. process (consume) {@param payload}
   */
  @MessageMapping("/api/v1/command/consume-stomp-message")
  public void receive(String payload) {
    log.info("received {}", payload);
  }

  /**
   * 1. receive clients {@param payload} to destination: `/api/v1/command/send-stomp-message`
   * 2. processed (transform) {@param payload}
   * 3. send {@return message} to the message broker ie, to all clients (subscribers)
   */
  @MessageMapping("/api/v1/command/send-stomp-message")
  @SendTo("/topic/test-messages")
  public String receiveAndSend(String payload) {
    val message = System.currentTimeMillis() + ": " + payload;
    log.info("relay: {}", message);
    return message;
  }

  /**
   * 1. handle http post request on: `/api/v1/command/send-stomp-message-manually`
   * 2. processed (transform) {@param payload}
   * 3. send {@return message} to the message broker manually to the destination: `/topic/test-messages`
   */
  @ResponseBody
  @PostMapping("/api/v1/command/send-stomp-message-manually")
  public void manualSend(@RequestBody String payload) {
    val message = System.currentTimeMillis() + ": " + payload;
    log.info("relay: {}", message);
    messagingTemplate.convertAndSend("/topic/test-messages", message);
  }
}
