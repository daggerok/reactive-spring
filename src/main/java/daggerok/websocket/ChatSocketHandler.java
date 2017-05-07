package daggerok.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import daggerok.domain.Message;
import daggerok.domain.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatSocketHandler extends TextWebSocketHandler {

  private final static CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
  private final static ObjectMapper mapper = new ObjectMapper();

  final MessageRepository messageRepository;

  @Override
  public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
    sessions.add(session);
  }

  @Override
  @Transactional
  protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {

    val document = from(message);
    messageRepository.save(document)
                     .subscribe(msg -> sessions.forEach(s -> send(s, message)));
  }

  @Override
  public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {

    log.warn("closing session: {}, status: {}", session, status);
    sessions.remove(session);
  }

  @SneakyThrows
  private static void send(final WebSocketSession session, final WebSocketMessage message) {

    try {

      session.sendMessage(message);

    } catch (Exception e) {

      log.warn("closing invalid session: {}", e.getMessage());
      sessions.remove(session);
    }
  }

  @SneakyThrows
  private static Message from(final WebSocketMessage message) {
    return mapper.reader().forType(Message.class).readValue(message.getPayload().toString());
  }
}
