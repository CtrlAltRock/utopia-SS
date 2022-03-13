package com.smoothstack.ua;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class UaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaApplication.class, args);
		System.out.println("here");
	}

}
