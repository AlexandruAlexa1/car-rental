package com.aa.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aa.domain.Role;
import com.aa.domain.User;
import com.aa.domain.UserPrincipal;
import com.aa.exception.DuplicateEmailException;
import com.aa.exception.NotFoundException;
import com.aa.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired private UserRepository repo;
	@Autowired private PasswordEncoder passwordEncoder;
	
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
	
	public User save(User userInForm) throws DuplicateEmailException {
		boolean isEditMode = userInForm.getId() != null;
		
		if (isEditMode) {
			User userInDB = repo.findById(userInForm.getId()).get();
			
			checkEmail(userInForm, userInDB);
			checkPassword(userInForm, userInDB);
		}
		
		checkDuplicateEmail(userInForm.getEmail());
		encodePassword(userInForm);
		
		return repo.save(userInForm);
	}
	
	private void checkPassword(User userInForm, User userInDB) {
		boolean isPasswordEmptry = userInForm.getPassword() == null;
		
		if (isPasswordEmptry) {
			userInForm.setPassword(userInDB.getPassword());
		}
		
		encodePassword(userInForm);
	}

	private void checkEmail(User userInForm, User userInDB) throws DuplicateEmailException {
		boolean isTheSameEmail = userInDB.getEmail().contentEquals(userInDB.getEmail());
		
		if (isTheSameEmail) {
			userInForm.setEmail(userInDB.getEmail());
		}
		
		checkDuplicateEmail(userInForm.getEmail());
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

	public User register(User user) throws DuplicateEmailException {
		checkDuplicateEmail(user.getEmail());
		encodePassword(user);
		setUserDetails(user);
		
		return repo.save(user);
	}

	private void setUserDetails(User user) {
		user.setJoinDate(new Date());
		user.setEnabled(true);
		user.setNotLocked(true);
		user.getRoles().add(new Role(2));
	}

	private void encodePassword(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
	}

	private void checkDuplicateEmail(String email) throws DuplicateEmailException {
		User userInDB = repo.findByEmail(email);
		
		if (userInDB != null) {
			throw new DuplicateEmailException("This E-mail: " + email + " already exist. Please choose another E-mail!");
		}
	}
}