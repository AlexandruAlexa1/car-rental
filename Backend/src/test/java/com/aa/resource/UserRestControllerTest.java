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

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.aa.domain.Address;
import com.aa.domain.Role;
import com.aa.domain.User;
import com.aa.exception.UserNotFoundException;
import com.aa.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class UserRestControllerTest {

	private static final String URI = "/api/v1/users";

	@MockBean private UserService service;
	
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper mapper;
	
	private User user_1;
	private User user_2;
	
	@BeforeEach
	void init() {
		Address address_1 = new Address("City", "State", "Country", "Postal Code", "Phone Number");
		Address address_2 = new Address("City", "State", "Country", "Postal Code", "Phone Number");
		
		user_1 = new User("admin@yahoo.com", "password", "Admin", "Admin", new Date(), new Date(), true, true, address_1, new Role(1));
		user_2 = new User("user@yahoo.com", "password", "Admin", "Admin", new Date(), new Date(), true, true, address_2, new Role(2));
	}
	
	@Test
	void listAll() throws Exception {
		when(service.findAll()).thenReturn(List.of(user_1, user_2));
		
		mockMvc.perform(get(URI))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(2)));
	}
	
	@Test
	void getUser() throws Exception {
		Integer id = 1;
		
		when(service.get(anyInt())).thenReturn(user_1);
		
		mockMvc.perform(get(URI + "/{id}", id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstName", is(user_1.getFirstName())));
	}
	
	@Test
	void getUser_UserNotFound() throws Exception {
		Integer id = 10;

		when(service.get(id)).thenThrow(new UserNotFoundException());
		
		mockMvc.perform(get(URI + "/{id}", id))
			.andExpect(status().isNotFound());
	}
	
	@Test
	void add() throws Exception {
		when(service.save(any(User.class))).thenReturn(user_1);
		
		mockMvc.perform(post(URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(user_1)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.email", is(user_1.getEmail())))
			.andExpect(jsonPath("$.firstName", is(user_1.getFirstName())));
	}
	
	@Test
	void update() throws JsonProcessingException, Exception {
		when(service.save(any(User.class))).thenReturn(user_1);
		
		mockMvc.perform(put(URI)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(user_1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.email", is(user_1.getEmail())));
	}
	
	@Test
	void testDelete() throws Exception {
		Integer id = 1;
		
		doNothing().when(service).delete(id);
		
		mockMvc.perform(delete(URI + "/{id}", id))
			.andExpect(status().isNoContent());
		
		verify(service).delete(id);
	}
	
	@Test
	void testDelete_UserNotFound() throws Exception {
	    Integer id = 10;

	    doThrow(new UserNotFoundException()).when(service).delete(id);

	    mockMvc.perform(delete(URI + "/{id}", id))
	        .andExpect(status().isNotFound());

	    verify(service).delete(id);
	}
}


















