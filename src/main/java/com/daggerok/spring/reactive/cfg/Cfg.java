package com.daggerok.spring.reactive.cfg;

import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Environment;
import reactor.core.composable.Deferred;
import reactor.core.composable.Stream;
import reactor.core.composable.spec.Streams;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import static java.lang.System.out;
import static java.util.stream.Stream.of;

@Configuration
public class Cfg {

    private static final Consumer printf = i -> out.printf("java:%s\n", i);

    @Bean
    public CommandLineRunner funcsJava() {

        val streamCls = java.util.stream.Stream.class;

        return args1 -> of(streamCls.getMethods())
                .filter(m -> streamCls.isAssignableFrom(m.getReturnType()))
                .map(Method::getName)
                .distinct()
                .forEach(printf);
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
