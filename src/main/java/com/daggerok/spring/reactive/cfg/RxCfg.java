package com.daggerok.spring.reactive.cfg;

import com.daggerok.spring.reactive.ReactiveSpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.out;
import static java.util.Arrays.asList;

@Configuration
@ComponentScan(basePackageClasses = ReactiveSpringApplication.class)
public class RxCfg {
    @Bean
    public CommandLineRunner runner() {
        return args -> reactor.rx.Streams
                .from(asList("one, two, three".split(", ")))
                .consume(out::println);
    }
}
