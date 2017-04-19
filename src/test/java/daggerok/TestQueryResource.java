package daggerok;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TestQueryResource {

  /**
   * 1. handle http sync request on: `/api/v1/query/get-sync-messages`
   * 2. fetch data {@return messages}
   * 3. respond (replay) {@return messages} back
   */
  @SubscribeMapping("/api/v1/query/subscribe/get-sync-messages")
//  public List<String> syncRequestReplay(Principal user) {
  public List<String> syncRequestReplay() {
    // todo
    val message = asList(
        "one",
        "two",
        "three"
    );
    log.info("sync respond: {}", message);
    return message;
  }
}
