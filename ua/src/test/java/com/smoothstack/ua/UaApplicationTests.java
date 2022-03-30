package com.smoothstack.ua;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smoothstack.ua.controllers.AdminController;
import com.smoothstack.ua.services.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class UaApplicationTests {

	@Test
	void contextLoads() {
	}

}
