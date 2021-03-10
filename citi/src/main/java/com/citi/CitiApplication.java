package com.citi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.citi.*"})
public class CitiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitiApplication.class, args);
	}

}
