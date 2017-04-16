package daggerok.functional.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;
import reactor.util.function.Tuple2;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.stream.Stream;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class SpringBeansApplication {

  /**
   * http :8888/ping/qwe
   */
  @Bean
  HandlerFunction<ServerResponse> pingHandler() {

    return request -> ServerResponse
        .ok()
        .body(Mono.justOrEmpty(request.pathVariable("id"))
                  .map("pong "::concat)
                  .log(), String.class);
  }

  /**
   * http --stream :8888
   */
  @Bean
  HandlerFunction<ServerResponse> homeHandler() {

    return request ->
        ServerResponse
            .ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(Flux.interval(Duration.ofSeconds(1)).map(String::valueOf), String.class);
  }

  /**
   * http --stream :8888/whatever..
   */
  @Bean
  HandlerFunction<ServerResponse> fallbackHandler() {

    return request ->
        ServerResponse
            .ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(Flux.zip(
                Flux.fromStream(Stream.generate(() -> "" + System.currentTimeMillis())),
                Flux.interval(Duration.ofSeconds(1)).map(String::valueOf))
                      .map(Tuple2::getT1)
                      .map(String::valueOf), String.class);
  }

  @Autowired HandlerFunction<ServerResponse> pingHandler;
  @Autowired HandlerFunction<ServerResponse> homeHandler;
  @Autowired HandlerFunction<ServerResponse> fallbackHandler;

  @Bean
  HttpHandler applicationHandlers() {

    return RouterFunctions.toHttpHandler(
        route(GET("/"), homeHandler)
            .and(route(GET("/ping/{id}"), pingHandler))
            .and(route(GET("/**"), fallbackHandler)));
  }

  @Bean
  ReactorHttpHandlerAdapter reactorAdapter() {
    return new ReactorHttpHandlerAdapter(applicationHandlers());
  }

  @Bean
  HttpServer server() {
    return HttpServer.create("localhost", 8888);
  }

  @Autowired HttpServer server;
  @Autowired ReactorHttpHandlerAdapter reactorAdapter;

  @PostConstruct
  public void bootstrap() throws IOException {

    server
        .newHandler(reactorAdapter)
        .block();
    // press enter to shutdown
    System.in.read();
  }

  @PreDestroy
  public void shutdown() {
    System.out.println("bye...");
  }

  public static void main(String[] args) {

    new SpringApplicationBuilder(SpringBeansApplication.class)
        .properties(Collections.singletonMap("server.port", "8888"))
        .run(args);
  }
}
