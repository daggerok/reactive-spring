package com.daggerok.spring.reactive;

import com.daggerok.spring.reactive.cfg.Cfg;
import com.daggerok.spring.reactive.cfg.RxCfg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({RxCfg.class, Cfg.class})
public class ReactiveSpringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(ReactiveSpringApplication.class, args);
        /*
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                app.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        */
        app.close();
    }
}
