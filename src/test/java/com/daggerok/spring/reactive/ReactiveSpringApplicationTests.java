package com.daggerok.spring.reactive;

import daggerok.OldWayMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {OldWayMain.class, ReactiveSpringApplication.class})
public class ReactiveSpringApplicationTests {

    @Autowired ApplicationContext applicationContext;

    private static final BiFunction<String, String, Boolean> is = (actual, expect) -> actual.toLowerCase().contains(expect);

    private static final Predicate<String> isOldWay = s -> is.apply(s, "foo") || is.apply(s, "bar");

    @Test
    public void contextLoads() {
        assertThat(Stream.of(applicationContext.getBeanDefinitionNames())
                           .filter(isOldWay).count(), greaterThanOrEqualTo(2L));
    }
}
