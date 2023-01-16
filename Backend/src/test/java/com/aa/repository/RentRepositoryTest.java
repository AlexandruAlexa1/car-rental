package com.aa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

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
	
	@Test
	void save() {
		Rent rent = new Rent(LocalDate.now(), LocalDate.now().plusDays(10), new Car(1), new User(1));

		Rent savedRent = repo.save(rent);
		
		assertNotNull(savedRent);
		assertThat(savedRent.getId()).isGreaterThan(0);
	}
}
