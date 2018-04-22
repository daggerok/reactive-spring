package daggerok;

import daggerok.functional.bean.SpringBeansApplication;
import daggerok.functional.vanilla.RoutesApplication;
import daggerok.reactive.client.WebClientApplication;
import daggerok.reactive.service.ReactiveWebfluxServiceApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.util.Objects.nonNull;

@Slf4j
@SpringBootApplication
public class Application {

  @SneakyThrows
  public static void main(String[] args) {

    final String[] arg = nonNull(args) && args.length > 0 ? args[0].split("=") : new String[]{"args", "5"};
    final String timeout = arg[1];

    new Thread(() -> RoutesApplication.main(new String[]{"3000", timeout})).start();
    new Thread(() -> SpringBeansApplication.main(new String[]{"8888"})).start();
    new Thread(() -> ReactiveWebfluxServiceApplication.main(new String[]{"8080"})).start();
    new Thread(() -> WebClientApplication.main(new String[]{"8000"})).start();

    log.info("application will shutdown in {} seconds...", timeout);
    Thread.currentThread().join();
  }
}
