package com.aa.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aa.domain.User;
import com.aa.exception.NotFoundException;
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
	
	public User get(Integer id) throws NotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Could not find any user with id: " + id);
		}
	}
	
	public void delete(Integer id) throws NotFoundException {
		Optional<User> findedUser = repo.findById(id);
		
		if (findedUser.isEmpty()) {
			throw new NotFoundException("Could not find any user with id: " + id);
		}
		
		repo.deleteById(id);
	}
}
