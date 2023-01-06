package com.aa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aa.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);
}
