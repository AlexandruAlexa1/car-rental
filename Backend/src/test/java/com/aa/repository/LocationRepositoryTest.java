package com.aa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.aa.domain.Location;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class LocationRepositoryTest {

	@Autowired private LocationRepository repo;
	
	@Test
	void save() {
		Location location = new Location("Address", "Phone Number", "E-mail", new ArrayList<>());
		
		Location savedLocation = repo.save(location);
		
		assertNotNull(savedLocation);
		assertThat(savedLocation.getId()).isGreaterThan(0);
	}
}