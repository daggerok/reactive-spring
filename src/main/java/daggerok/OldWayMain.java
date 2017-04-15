package daggerok;

import daggerok.svc.BarService;
import daggerok.svc.FooService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class OldWayMain {

    @Bean
    BarService barService(FooService fooService) {
        return new BarService(fooService);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(OldWayMain.class);
    }
}
