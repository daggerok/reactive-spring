package daggerok;

import daggerok.domain.TestDataConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ TestDataConfig.class })
public class SpringBootReactiveWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactiveWebApplication.class, args);
    }
}
