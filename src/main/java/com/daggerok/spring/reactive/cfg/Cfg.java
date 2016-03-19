package com.daggerok.spring.reactive.cfg;

import com.daggerok.spring.reactive.ReactiveSpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.core.Environment;
import reactor.core.composable.Deferred;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;

@Configuration
@ComponentScan(basePackageClasses = ReactiveSpringApplication.class)
public class Cfg {
    @Bean
    public CommandLineRunner runner() {
        return args -> {
            Deferred<Object, Stream<Object>> stream = Streams.defer()
                    .env(new Environment())
                    .dispatcher(Environment.RING_BUFFER)
                    .get();

            stream.compose().<String>consume(s -> System.out.println(String.valueOf(s).toUpperCase()));
            stream.accept("reactor");
        };
    }
}
