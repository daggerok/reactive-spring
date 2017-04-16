package daggerok.functional.vanilla;

import lombok.val;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class RoutesApplication {

  public static void main(String[] args) throws IOException {

    val routes = route(
        GET("/ping/{id}"), request ->
            ServerResponse.ok().body(
                Mono.justOrEmpty(request.pathVariable("id"))
                    .map(String::valueOf)
                    .map(String::toUpperCase)
                    .log(), String.class));

    val httpHandler = RouterFunctions.toHttpHandler(routes);
    val adapter = new ReactorHttpHandlerAdapter(httpHandler);

    HttpServer.create("localhost", 8000)
        .newHandler(adapter)
        .block();
    System.in.read();
  }
}
