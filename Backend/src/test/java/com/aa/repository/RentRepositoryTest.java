package com.aa.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.aa.domain.Car;
import com.aa.domain.Rent;
import com.aa.domain.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RentRepositoryTest {

	@Autowired private RentRepository repo;
	
	Rent rent_1;
	Rent rent_2;
	
	@BeforeEach
	public void init() {
		rent_1 = new Rent(LocalDate.of(2023, 01, 01), LocalDate.of(2023, 01, 15), new Car(), new User());
		rent_2 = new Rent(LocalDate.of(2023, 10, 01), LocalDate.of(2023, 11, 12), new Car(), new User());
	}
	
	@Test
	void save() {
		repo.save(rent_1);
	}
}
