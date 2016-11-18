package daggerok.domain;

import daggerok.domain.user.model.User;
import daggerok.domain.user.repository.UserMongoRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = {User.class, UserMongoRepository.class})
public class TestDataConfig {

    @Bean
    public CommandLineRunner init(final UserMongoRepository userMongoRepository) {

        val count = userMongoRepository.count();

        if (count > 0) {
            return args -> log.info("{} users already exists in the database", count);
        }

        return args -> userMongoRepository.save(
                Stream.of("Max", "Bax", "Fax")
                        .map(name -> new User().setUsername(name))
                        .collect(toList()));
    }
}
