package com.aa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aa.domain.Address;
import com.aa.domain.Role;
import com.aa.domain.User;
import com.aa.exception.UserNotFoundException;
import com.aa.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repo;

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
	void findAll() {
		when(repo.findAll()).thenReturn(List.of(user_1, user_2));
		
		List<User> listUsers = service.findAll();
		
		assertNotNull(listUsers);
		assertEquals(2, listUsers.size());
		
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	void save() {
		when(repo.save(any(User.class))).thenReturn(user_1);
		
		User savedUser = service.save(user_1);
		
		assertNotNull(savedUser);
		assertEquals(user_1.getEmail(), savedUser.getEmail());
	}
	
	@Test
	void get() throws UserNotFoundException {
		Integer id = 1;
		
		when(repo.findById(anyInt())).thenReturn(Optional.of(user_1));
		
		User findedUser = service.get(id);
		
		assertNotNull(findedUser);
	}
	
	@Test
	void getForException() {
		when(repo.findById(1)).thenReturn(Optional.of(user_1));
		
		assertThrows(Exception.class, () -> {
			service.get(10);
		});
	}
	
	@Test
	void delete() throws UserNotFoundException {
		Integer id = 1;
		
		when(repo.existsById(anyInt())).thenReturn(true);
		
		doNothing().when(repo).deleteById(anyInt());
		
		service.delete(id);
		
		verify(repo, times(1)).deleteById(id);
	}
	
	@Test
	void deleteForException() throws UserNotFoundException {
		when(repo.existsById(1)).thenReturn(false);
		
		doNothing().when(repo).deleteById(anyInt());
		
		assertThrows(Exception.class, () -> {
			service.delete(10);
			verify(repo, times(1)).deleteById(10);
		});
	}
	
}