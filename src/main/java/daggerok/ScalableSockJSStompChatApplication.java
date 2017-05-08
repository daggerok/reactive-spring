package daggerok;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Collections;

@SpringBootApplication
public class ScalableSockJSStompChatApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(ScalableSockJSStompChatApplication.class)
        .properties(Collections.singletonMap("server.port", "3000"))
        .run(args);
  }
}
