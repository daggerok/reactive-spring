package daggerok;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Collections;

@SpringBootApplication
public class SseApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(SseApplication.class)
        .properties(Collections.singletonMap("server.port", "3000"))
        .run(args);
  }
}
