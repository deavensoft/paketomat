package com.deavensoft.paketomat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.deavensoft.paketomat.center"})
public class PaketomatApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaketomatApplication.class, args);
	}

}
