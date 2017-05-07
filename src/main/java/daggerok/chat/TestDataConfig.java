package daggerok.chat;

import daggerok.chat.domain.Message;
import daggerok.chat.domain.MessageRepository;
import daggerok.chat.domain.User;
import daggerok.chat.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class TestDataConfig {

  @Bean
  @Transactional
  public CommandLineRunner initDb(final UserRepository userRepository,
                                  final MessageRepository messageRepository) {
///*
    return args -> userRepository.deleteAll()
                                 .thenMany(
                                     Flux.just("Max", "Bax")
                                         .map(name -> new User().setUsername(name))
                                         .map(userRepository::save)
                                         .map(Mono::block)
                                         .map(User::getUsername)
                                         .map(username -> new Message().setOwner(username))
                                         .map(message -> message.setBody("hi, " + message.getOwner() + "!"))
                                         .map(messageRepository::save)
                                         .map(Mono::block)
                                 ).blockLast();
//*/
/*
    return args -> userRepository.deleteAll()
                                 .thenMany(
                                     messageRepository.saveAll(
                                         userRepository.saveAll(Flux.just("Max", "Bax")
                                                                    .map(name -> new User().setUsername(name)))
                                                       .map(User::getUsername)
                                                       .map(username -> new Message().setOwner(username))
                                                       .map(message -> message.setBody("hi, " + message.getOwner() + "!"))))
                                 .blockLast();
*/
  }
}
