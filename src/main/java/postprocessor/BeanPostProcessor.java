package postprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanPostProcessor {

    public static void main(String[] args) {
        SpringApplication.run(BeanPostProcessor.class, args);
    }
}
