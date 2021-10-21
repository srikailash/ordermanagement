package com.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableTransactionManagement
@EnableRetry
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
