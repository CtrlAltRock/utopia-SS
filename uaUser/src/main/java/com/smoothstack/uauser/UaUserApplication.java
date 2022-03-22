package com.smoothstack.uauser;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Utopia Airlines API", version = "3.0", description = "Utopia Airlines Information"))
public class UaUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UaUserApplication.class, args);
		System.out.println("here");
	}

}
