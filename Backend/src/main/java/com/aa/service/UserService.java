package com.aa.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aa.domain.User;
import com.aa.exception.UserNotFoundException;
import com.aa.repository.UserRepository;

@Service
public class UserService {

	@Autowired private UserRepository repo;
	
	public List<User> findAll() {
		return repo.findAll();
	}
	
	public User save(User user) {
		return repo.save(user);
	}
	
	public User get(Integer id) throws UserNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException("Could not find any user with id: " + id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		if (!repo.existsById(id)) {
			throw new UserNotFoundException("Could not find any user with id: " + id);
		}
		
		repo.deleteById(id);
	}
}
