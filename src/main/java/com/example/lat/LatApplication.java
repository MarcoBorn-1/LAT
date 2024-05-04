package com.example.lat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.lat.repository")
public class LatApplication {
	public static void main(String[] args) {
		SpringApplication.run(LatApplication.class, args);
	}

}
