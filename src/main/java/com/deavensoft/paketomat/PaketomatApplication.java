package com.deavensoft.paketomat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EntityScan("com.deavensoft.paketomat")
public class PaketomatApplication {

	public static void main(String[] args) {

		SpringApplication.run(PaketomatApplication.class, args);
	}

}
