package postprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeanPostProcessor {

    public static void main(String[] args) {
        SpringApplication.run(BeanPostProcessor.class, args);
    }
}
