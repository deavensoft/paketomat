package com.deavensoft.paketomat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
@ComponentScan
@EnableJpaRepositories(basePackages = {"com.deavensoft.paketomat"})
class PaketomatApplicationTests {

	@Test
	void contextLoads() {

	}

}
