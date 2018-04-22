package daggerok.functional.vanilla;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class RoutesApplication {

  @SneakyThrows
  public static void main(String[] args) {

    final RouterFunction<ServerResponse> routes =
        route(GET("/ping/{id}"), request ->
            ServerResponse.ok().body(
                Mono.justOrEmpty(request.pathVariable("id"))
                    .map(String::valueOf)
                    .map(String::toUpperCase)
                    .log(), String.class));

    final HttpHandler httpHandler = RouterFunctions.toHttpHandler(routes);
    final ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
    final int port = nonNull(args) && args.length > 0 ? Integer.parseUnsignedInt(args[0]) : 8000;

    HttpServer.create("localhost", port)
              .newHandler(adapter)
              .block();

    final long until = nonNull(args) && args.length > 1 ? Long.parseLong(args[1]) : -1L;
    final LocalDateTime start = now();
    final LocalDateTime end = start.plusSeconds(until);

    while (until == -1L || start.isBefore(end)) {
      TimeUnit.SECONDS.sleep(1);
    }

    System.out.println("scheduled shutdown...");
    System.exit(0);
  }
}
