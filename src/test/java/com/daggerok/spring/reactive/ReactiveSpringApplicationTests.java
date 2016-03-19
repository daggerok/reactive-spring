package com.daggerok.spring.reactive;

import com.daggerok.spring.reactive.cfg.Cfg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Cfg.class)
@WebAppConfiguration
public class ReactiveSpringApplicationTests {

	@Test
	public void contextLoads() {
	}

}
