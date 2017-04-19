package daggerok.chat.resource;

import daggerok.chat.resource.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@RestController
public class MessagePubSubResource {

  @MessageMapping("/api/v1/pub-sub/send-message")
  @SendTo("/topic/chat-messages")
  public Message broadcastMessage(@RequestBody Message message) {
    return message.setBody(format("says: %s", message.body));
  }
}
