package com.smoothstack.ua;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Utopia Airlines API", version = "1.0", description = "Utopia Airlines Information"))
public class UaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaApplication.class, args);
		System.out.println("here");
	}

}
