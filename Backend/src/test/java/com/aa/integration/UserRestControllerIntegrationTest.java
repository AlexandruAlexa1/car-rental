package com.aa.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.aa.domain.Address;
import com.aa.domain.Role;
import com.aa.domain.User;
import com.aa.exception.UserNotFoundException;
import com.aa.repository.UserRepository;
import com.aa.service.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerIntegrationTest {

	private static String URI;

	@LocalServerPort
	private Integer port;
	
	private RestTemplate restTemplate;
	
	@Autowired private UserRepository userRepository;
	@Autowired private UserService service;

	private User user_1;
	private User user_2;
	
	@BeforeEach
	public void before() {
		URI = String.format("http://localhost:%d/api/v1/users", port);
		
		restTemplate = new RestTemplate();
		
		Address address_1 = new Address("City", "State", "Country", "Postal Code", "Phone Number");
		Address address_2 = new Address("City", "State", "Country", "Postal Code", "Phone Number");
		
		user_1 = new User("admin@yahoo.com", "password", "Admin", "Admin", new Date(), new Date(), true, true, address_1, new Role(1));
		user_2 = new User("user@yahoo.com", "password", "Admin", "Admin", new Date(), new Date(), true, true, address_2, new Role(2));
	}
	
	@AfterEach
	public void after() {
		userRepository.deleteAll();
	}
	
	@Test
	@Description("It should return all users")
	void listAll() {
		userRepository.saveAll(List.of(user_1, user_2));
		
		ResponseEntity<List<User>> response = 
				restTemplate.exchange(URI, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
		
		List<User> listUsers = response.getBody();
		
		assertNotNull(listUsers);
		assertEquals(2, listUsers.size());
	}
	
	@Test
	@Description("It should return user by id")
	void get() {
		User savedUser = userRepository.save(user_1);
		
		User existingUser = restTemplate.getForObject(URI + "/" + savedUser.getId(), User.class);
		
		assertNotNull(existingUser);
		assertEquals(savedUser.getFirstName(), existingUser.getFirstName());
		assertEquals(savedUser.getLastName(), existingUser.getLastName());
		assertEquals(savedUser.getEmail(), existingUser.getEmail());
	}
	
	@Test
	@Description("It should throw UserNotFoundException is user not found")
	void get_UserNotFoundException() {
		assertThrows(UserNotFoundException.class, () -> service.get(10));
	}
	
	@Test
	@Description("It should add a new user and return 201 status code")
	void add() {
		ResponseEntity<User> response = restTemplate.postForEntity(URI, user_1, User.class);
		
		User savedUser = response.getBody();
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(savedUser);
		assertEquals(user_1.getEmail(), savedUser.getEmail());
	}
	
	@Test
	@Description("It should delete user by id")
	void delete() {
		User savedUser = userRepository.save(user_1);
		
		restTemplate.delete(URI + "/" + savedUser.getId());
		
		int count = userRepository.findAll().size();
		assertEquals(0, count);
	}
	
	@Test
	@Description("It should throw UserNotFoundException is user not found")
	void delete_UserNotFoundException() {
		assertThrows(UserNotFoundException.class, () -> service.delete(10));
	}
	
	@Test
	@Description("It should return 404 status code if user not found")
	void delete_NotFound() {
	    Integer id = 10;

	    try {
	        restTemplate.delete(URI + "/" + id);
	        fail("An exception was expected");
	    } catch (HttpClientErrorException e) {
	        assertEquals(404, e.getStatusCode().value());
	    }
	}
}