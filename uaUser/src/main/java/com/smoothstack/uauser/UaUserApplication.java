package com.smoothstack.uauser;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Utopia Airlines API", version = "1.0", description = "Utopia Airlines Information"))
public class UaUserApplication {

	private static Logger logger = LoggerFactory.getLogger(UaUserApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UaUserApplication.class, args);
		logger.info("Server Startup Finished");
	}
}
