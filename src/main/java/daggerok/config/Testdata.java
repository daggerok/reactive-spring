package daggerok.config;

import daggerok.data.BusinessObject;
import daggerok.data.BusinessObjectRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

@Slf4j
@Profile({ "dev", "test" })
public class Testdata {

  @Bean
  public CommandLineRunner init(final BusinessObjectRepo repo) {

    return args -> repo.save(
        Flux.fromStream(
            Stream.of(new BusinessObject("one"),
                      new BusinessObject("two"))))
                       .thenMany(repo.findAll()
                                     /*
                                     .thenMany(s -> log.info("done: {}", repo.findAll()
                                                                             .toStream()
                                                                             .collect(toList()))))
                                     */
                                     .thenMany(s -> log.info("done: {}", repo.findFirst(2L)
                                                                             .block())))
                       .blockLast();
  }
}
