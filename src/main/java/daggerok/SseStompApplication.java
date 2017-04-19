package daggerok;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;

@ComponentScan
@SpringBootApplication
public class SseStompApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(SseStompApplication.class)
        .properties(Collections.singletonMap("server.port", "3000"))
        .run(args);
  }
}
