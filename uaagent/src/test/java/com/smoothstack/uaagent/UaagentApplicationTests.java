package com.smoothstack.uaagent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smoothstack.uaagent.models.Booking;
import com.smoothstack.uaagent.models.Flight;
import com.smoothstack.uaagent.models.Passenger;
import com.smoothstack.uaagent.repos.BookingRepository;
import com.smoothstack.uaagent.security.jwt.JwtRequest;
import com.smoothstack.uaagent.security.jwt.JwtTokenUtil;
import com.smoothstack.uaagent.security.jwt.JwtUserDetailsService;
import com.smoothstack.uaagent.security.repositories.UserDao;
import com.smoothstack.uaagent.services.AgentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.security.access.method.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest//(controllers = AgentController.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UaagentApplicationTests {

	@MockBean
	private AgentService agentService;

	@MockBean
	private JwtUserDetailsService jwtService;

	@MockBean
	private BookingRepository bookingRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Generators generator = new Generators();

	private List<Passenger> passengers = IntStream.range(0, 5).boxed().map( (x) -> generator.makePassenger(x)).collect(Collectors.toList());

	private Booking mainBooking;

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
	@Order(1)
	public void emptyFlights() throws Exception {
		Mockito.when(agentService.getFlights())
				.thenReturn(Arrays.asList());

		mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/flights/all"))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()", Matchers.is(0)));
	}

	@Test
	@Order(2)
	public void getSomeFlights() throws Exception {
		Mockito.when(agentService.getFlights())
				.thenReturn(Arrays.asList(new Flight(), new Flight()));

		mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/flights/all"))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()", Matchers.is(2)));
	}

	@Test
	@Order(3)
	public void postPassenger() throws Exception {
		Passenger passenger = passengers.get(1);
		passenger.setId(null);

		Mockito.when(agentService.getBookingById(passenger.getBooking_id().getId())).thenReturn(passenger.getBooking_id());
		Mockito.when(agentService.postPassenger(any(Passenger.class)))
				.thenReturn(passenger);

		mockMvc.perform(MockMvcRequestBuilders.post("/utopia/airlines/passengers")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(asJsonString(passengers.get(1)))
						.characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
				//.andExpect(jsonPath("$.getId()", Matchers.is(1)));
	}

	@Test
	@Order(4)
	public void getAllPassengers() throws Exception {
		Mockito.when(agentService.getPassengers())
				.thenReturn(passengers);

		mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/passengers/"))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()", Matchers.is(5)));
	}

	@Test
	@Order(5)
	public void getPassengerById() throws Exception {
		Mockito.when(agentService.getPassengerById(1))
				.thenReturn(passengers.get(1));

		mockMvc.perform(MockMvcRequestBuilders.get("/utopia/airlines/passengers/1"))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
//				.andExpect(jsonPath("$.getId()", Matchers.is(1)));
	}



	@Test
	@Order(6)
	public void putPassenger() throws Exception {
		Passenger passenger = passengers.get(2);
		Mockito.when(agentService.postPassenger(any(Passenger.class)))
				.thenReturn(passenger);

		mockMvc.perform(MockMvcRequestBuilders.put("/utopia/airlines/passengers/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(passenger)))
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}


/*	@Test
	@Order(1)
	public void deletePassenger() throws Exception {
		Mockito.when(agentService.deletePassenger(passenger.getId()))
				.thenReturn(passenger);

		mockMvc.perform(MockMvcRequestBuilders.delete("/utopia/airlines/passengers/1"))
				.andExpect(status().is(200));
	}*/





}
