package daggerok;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RenderingResponse;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static java.util.Collections.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.web.reactive.function.server.RenderingResponse.create;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
class ApplicationHandlers {

/*
  Mono<ServerResponse> index(final ServerRequest request) {
    return ok().contentType(TEXT_HTML)
               .render("index", singletonMap("message", "Hello, World!"))
        ;
  }
*/

  Mono<RenderingResponse> index(final ServerRequest request) {
    return create("index").modelAttribute("message", "Hello, World!")
                          .build()
        ;
  }

  Mono<ServerResponse> api(final ServerRequest request) {
    return ok().contentType(APPLICATION_JSON_UTF8)
               .body(Mono.just("Hello World!"), String.class)
        ;
  }
}

@Slf4j
@SpringBootApplication
public class Application {

  @Bean
  RouterFunction routes(final ApplicationHandlers handlers) {
    return route(GET("/api/**").and(accept(APPLICATION_JSON_UTF8)), handlers::api)
        .andOther(route(GET("/").and(accept(TEXT_HTML)), handlers::index))
        ;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
