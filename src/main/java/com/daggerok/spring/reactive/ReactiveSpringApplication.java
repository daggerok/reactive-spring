package com.daggerok.spring.reactive;

import com.daggerok.spring.reactive.cfg.Cfg;
import com.daggerok.spring.reactive.cfg.RxCfg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({RxCfg.class, Cfg.class})
public class ReactiveSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringApplication.class, args).close();
    }
}
