package daggerok;

import daggerok.config.Testdata;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication
@Import({ Testdata.class })
public class Application {

  @SneakyThrows
  public static void main(String[] args) {

    SpringApplication.run(Application.class)
                     .registerShutdownHook();
  }
}
