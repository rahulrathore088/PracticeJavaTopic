package com.lp.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "{com.lp}")
public class LearningPlatformCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningPlatformCoreApplication.class, args);
	}

}
