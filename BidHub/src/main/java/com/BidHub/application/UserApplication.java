package com.BidHub.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com")
@EnableJpaRepositories(basePackages = "com.Bidhub.repository")
@EntityScan("com.BidHub.entity")
public class UserApplication {

	public static void main(String... args) {
		SpringApplication.run(UserApplication.class, args);
	}
}