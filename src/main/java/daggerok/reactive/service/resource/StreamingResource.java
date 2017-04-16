package daggerok.reactive.service.resource;

import daggerok.payload.Event;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
public class StreamingResource {

  /**
   * http :8080/1
   */
  @GetMapping("/{id}")
  public Mono<Event> event(@PathVariable final String id) {
    return Mono.just(Event.of("" + id));
  }

  /**
   * http --stream :8080
   */
  @GetMapping(produces = TEXT_EVENT_STREAM_VALUE)
  public Flux<Event> events() {

    val curr = Flux.fromStream(Stream.generate(() -> Event.of("" + System.currentTimeMillis())));
    val each = Flux.interval(Duration.ofMillis(2500));

    return Flux.zip(curr, each)
               .map(Tuple2::getT1)
               .log();
  }
}
