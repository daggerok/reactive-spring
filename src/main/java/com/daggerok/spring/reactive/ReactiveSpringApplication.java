package com.daggerok.spring.reactive;

import com.daggerok.spring.reactive.cfg.Cfg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(Cfg.class)
@SpringBootApplication
public class ReactiveSpringApplication {

    public static void main(String[] args) {
        SpringApplication
                .run(ReactiveSpringApplication.class, args)
                .close();
    }
}
