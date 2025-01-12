package com.rafaelhosaka.rhv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RhvApplication {

	public static void main(String[] args) {
		SpringApplication.run(RhvApplication.class, args);
	}

}
