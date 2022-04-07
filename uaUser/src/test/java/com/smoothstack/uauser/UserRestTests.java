package com.smoothstack.uauser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smoothstack.uauser.models.Flight;
import com.smoothstack.uauser.security.jwt.JwtUserDetailsService;
import com.smoothstack.uauser.services.UserService;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRestTests {

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUserDetailsService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(value = "user", roles = {"USER"})
    public void getAllFlights() throws Exception {
        Mockito.when(userService.getAvailableFlights()).thenReturn(Arrays.asList(new Flight(), new Flight()));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/flights/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(value = "user", roles = {"USER"})
    public void getUserFlights() throws Exception {
        Mockito.when(userService.getUserFlights()).thenReturn(Arrays.asList(new Flight(), new Flight(), new Flight()));

        mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/myflights/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(value = "user", roles = {"USER"})
    public void postPassenger() {
        
    }

}
