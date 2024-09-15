package com.tutorial.kafka.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tutorial.kafka"})
public class KafkaTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaTutorialApplication.class, args);
	}

}
