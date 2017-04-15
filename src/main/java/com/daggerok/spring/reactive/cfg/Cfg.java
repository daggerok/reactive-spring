package com.daggerok.spring.reactive.cfg;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Environment;
import reactor.core.composable.Deferred;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;

import java.lang.reflect.Method;

import static java.lang.System.out;

@Configuration
public class Cfg {
    @Bean
    public CommandLineRunner funcsJava() {
        return args1 -> java.util.stream.Stream.of(java.util.stream.Stream.class.getMethods())
                .filter(m -> java.util.stream.Stream.class.isAssignableFrom(m.getReturnType()))
                .map(Method::getName)
                .distinct()
                .forEach(i -> out.printf("java:%s\n", i));
    }

    @Bean
    public CommandLineRunner runner2() {
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
