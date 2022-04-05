package com.smoothstack.uaagent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UaagentApplication {

	static Logger logger = LoggerFactory.getLogger(UaagentApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UaagentApplication.class, args);
		logger.info("Server StartUp Finished");
	}

}
