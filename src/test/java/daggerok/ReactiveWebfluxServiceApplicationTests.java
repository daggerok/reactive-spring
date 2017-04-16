package daggerok;

import daggerok.reactive.client.WebClientApplication;
import daggerok.functional.bean.SpringBeansApplication;
import daggerok.functional.vanilla.RoutesApplication;
import daggerok.reactive.service.ReactiveWebfluxServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = {
        ReactiveWebfluxServiceApplication.class,
        WebClientApplication.class,
        RoutesApplication.class,
        SpringBeansApplication.class,
    })
public class ReactiveWebfluxServiceApplicationTests {

  @Test
  public void contextLoads() {}
}
