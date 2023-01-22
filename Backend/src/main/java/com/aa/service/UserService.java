package com.aa.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aa.domain.User;
import com.aa.domain.UserPrincipal;
import com.aa.exception.NotFoundException;
import com.aa.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found by E-mail: " + email);
		}
		
		user.setLastLoginDate(new Date());
		
		return new UserPrincipal(user);
	}
	
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
	
	public User findByEmail(String email) throws NotFoundException {
		try {
			return repo.findByEmail(email);
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Could not find any user with email: " + email);
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
