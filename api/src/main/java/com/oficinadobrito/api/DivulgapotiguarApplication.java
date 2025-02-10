package com.oficinadobrito.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class DivulgapotiguarApplication {

	public static void main(String[] args) {
		SpringApplication.run(DivulgapotiguarApplication.class, args);
	}

}
