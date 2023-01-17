package com.aa.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.aa.domain.Location;
import com.aa.exception.NotFoundException;
import com.aa.service.CarService;
import com.aa.service.LocationService;
import com.aa.service.RentService;
import com.aa.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@MockBeans(value = {
		@MockBean(CarService.class),
		@MockBean(RentService.class),
		@MockBean(UserService.class),
})
public class LocationRestControllerTest {
	
	private static final String URI = "/api/v1/locations";

	@MockBean private LocationService service;
	
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	private Location location_1;
	private Location location_2;
	
	@BeforeEach
	public void init() {
		location_1 = new Location("Street of Light nr. 10", "0761827384", "location_1@yahoo.com", null);
		location_2 = new Location("Street of the Sun nr. 10", "0761827222", "location_2@yahoo.com", null);
	}
	
	@Test
	void listAll() throws Exception {
		when(service.findAll()).thenReturn(List.of(location_1, location_2));
		
		mockMvc.perform(get(URI))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(2)));
	}
	
	@Test
	void add() throws JsonProcessingException, Exception {
		when(service.save(any(Location.class))).thenReturn(location_1);
		
		mockMvc.perform(post(URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(location_1)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.address", is(location_1.getAddress())))
		.andExpect(jsonPath("$.phoneNumber", is(location_1.getPhoneNumber())));
	}
	
	@Test
	void getLocation() throws Exception {
		Integer id = 1;
		
		when(service.get(anyInt())).thenReturn(location_1);
		
		mockMvc.perform(get(URI + "/{id}", id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.email", is(location_1.getEmail())));
	}
	
	@Test
	void getLocation_NotFound() throws Exception {
		Integer id = 1;
		
		when(service.get(anyInt())).thenThrow(new NotFoundException());
		
		mockMvc.perform(get(URI + "/{id}", id))
			.andExpect(status().isNotFound());
	}
	
	@Test
	void update() throws JsonProcessingException, Exception {
		when(service.save(any(Location.class))).thenReturn(location_1);
		
		mockMvc.perform(put(URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(location_1)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.address", is(location_1.getAddress())));
	}
	
	@Test
	void deleteLocation() throws Exception {
		Integer id = 1;
		
		doNothing().when(service).delete(id);
		
		mockMvc.perform(delete(URI + "/{id}", id))
			.andExpect(status().isNoContent());
		
		verify(service).delete(id);
	}
	
	@Test
	void deleteLocation_NotFound() throws Exception {
		Integer id = 1;
		
		doThrow(new NotFoundException()).when(service).delete(id);
		
		mockMvc.perform(delete(URI + "/{id}", id))
		.andExpect(status().isNotFound());
		
		verify(service).delete(id);
	}
}