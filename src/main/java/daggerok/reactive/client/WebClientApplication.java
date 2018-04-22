package daggerok.reactive.client;

import daggerok.payload.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@Slf4j
@SpringBootApplication
public class WebClientApplication {

  @Bean
  WebClient webClient() {
    return WebClient.create("http://0.0.0.0:8080");
  }

  @Bean
  Consumer<? super Throwable> onError() {
    return err -> log.error("\n\noops: {}\n\n", err);
  }

  @Bean
  CommandLineRunner client() {

    return args -> webClient()
        .get()
        .uri("/")
        .accept(TEXT_EVENT_STREAM)
        .exchange()
        .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Event.class))
        .map(Event::getPayload)
        .subscribe(s -> log.info("{}", s));
  }

  public static void main(String[] args) {

    final String port = nonNull(args) && args.length > 0 ? args[0] : "3000";

    new SpringApplicationBuilder(WebClientApplication.class)
        .properties(Collections.singletonMap("server.port", port))
        .run(args);
  }
}
