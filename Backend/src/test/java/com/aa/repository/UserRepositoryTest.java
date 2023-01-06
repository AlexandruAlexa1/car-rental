package com.aa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.aa.domain.Address;
import com.aa.domain.Role;
import com.aa.domain.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

	@Autowired private UserRepository repo;
	
	private User user_1;
	private User user_2;
	
	@BeforeEach
	void init() {
		String password = "password";

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		
		Address address_1 = new Address("City", "State", "Country", "Postal Code", "Phone Number");
		Address address_2 = new Address("City", "State", "Country", "Postal Code", "Phone Number");
		
		user_1 = new User("admin@yahoo.com", encodedPassword, "Admin", "Admin", new Date(), new Date(), true, true, address_1, new Role(1));
		user_2 = new User("user@yahoo.com", encodedPassword, "Admin", "Admin", new Date(), new Date(), true, true, address_2, new Role(2));
	}
	
	@Test
	void save() {
		User savedUser = repo.save(user_2);
		
		assertNotNull(savedUser);
		assertThat(savedUser.getId()).isGreaterThan(0);
		assertEquals(savedUser.getEmail(), user_2.getEmail());
	}
	
	@Test
	void listAll() {
		List<User> listUsers = repo.findAll();
		
		assertNotNull(listUsers);
		assertEquals(2, listUsers.size());
		
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	void get() {
		Integer id = 1;
		
		User findedUser = repo.findById(id).get();
		
		assertNotNull(findedUser);
		assertEquals(user_1.getEmail(), findedUser.getEmail());
	}
	
	@Test
	void update() {
		Integer id = 1;
		String firstName = "Administrator";
		
		User findedUser = repo.findById(id).get();
		findedUser.setFirstName(firstName);
		
		User updatedUser = repo.save(findedUser);
		
		assertEquals(firstName, updatedUser.getFirstName());
	}
	
	@Test
	void delete() {
		Integer id = 2;
		
		repo.deleteById(id);
		
		Optional<User> findedUser = repo.findById(id);
		
		assertThat(!findedUser.isPresent());
	}
	
	@Test
	void findByEmail() {
		String email = "admin@yahoo.com";
		
		User findedUser = repo.findByEmail(email);
		
		assertNotNull(findedUser);
		assertEquals(email, findedUser.getEmail());
	}
}












