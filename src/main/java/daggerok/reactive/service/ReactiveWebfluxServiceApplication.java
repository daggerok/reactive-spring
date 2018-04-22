package daggerok.reactive.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Collections;

import static java.util.Objects.nonNull;

@SpringBootApplication
public class ReactiveWebfluxServiceApplication {

  public static void main(String[] args) {

    final String port = nonNull(args) && args.length > 0 ? args[0] : "8080";
    // SpringApplication.run(ReactiveWebfluxServiceApplication.class, args);
    new SpringApplicationBuilder(ReactiveWebfluxServiceApplication.class)
        .properties(Collections.singletonMap("server.port", port))
        .run(args);
  }
}
