package daggerok.reactive.client;

import daggerok.payload.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

@Slf4j
@SpringBootApplication
public class WebClientApplication {

  @Bean
  WebClient webClient() {
    return WebClient.create("http://localhost:8080");
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
        .accept(MediaType.TEXT_EVENT_STREAM)
        .exchange()
        .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Event.class))
        .map(Event::getPayload)
        .subscribe(log::info);
  }

  public static void main(String[] args) {

    String port = nonNull(args) && args.length > 0 ? args[0] : "3000";

    new SpringApplicationBuilder(WebClientApplication.class)
        .properties(Collections.singletonMap("server.port", port))
        .run(args);
  }
}
