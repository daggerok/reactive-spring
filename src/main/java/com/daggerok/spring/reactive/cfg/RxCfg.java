package com.daggerok.spring.reactive.cfg;

import com.daggerok.spring.reactive.ReactiveSpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import rx.Observable;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.out;
import static java.util.Arrays.asList;

@Configuration
@ComponentScan(basePackageClasses = ReactiveSpringApplication.class)
public class RxCfg {

    @Bean
    public CommandLineRunner funcsRx() {
        return args -> Observable.from(Observable.class.getMethods())
                .filter(m -> Observable.class.isAssignableFrom(m.getReturnType()))
                .map(Method::getName)
                .distinct()
                .forEach(i -> out.printf("rx:%s\n", i));
    }

    @Bean
    public CommandLineRunner rxRunner2() {
        Observable.just(IntStream.range(0, 3)
                                .mapToObj(String::valueOf)
                                .collect(Collectors.toSet()))
                .take(2)
                .doOnRequest(aLong -> out.print(String.format("aLong: %s\n", aLong)))
                .subscribe(out::println);

        return args -> reactor.rx.Streams
                .from(asList("one, two, three".split(", ")))
                .log()
                .capacity(2)
                .consume(out::println);
    }
}
