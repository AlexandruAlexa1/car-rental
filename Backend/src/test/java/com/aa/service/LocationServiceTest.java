package com.aa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aa.domain.Location;
import com.aa.exception.NotFoundException;
import com.aa.repository.LocationRepository;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

	@InjectMocks
	private LocationService service;
	
	@Mock
	private LocationRepository repo;

	private Location location_1;
	private Location location_2;
	
	@BeforeEach
	public void init() {
		location_1 = new Location("Street of Light nr. 10", "0761827384", "location_1@yahoo.com", null);
		location_2 = new Location("Street of the Sun nr. 10", "0761827222", "location_2@yahoo.com", null);
	}
	
	@Test
	void findAll() {
		when(repo.findAll()).thenReturn(List.of(location_1, location_2));
		
		List<Location> listLocations = service.findAll();
		
		assertNotNull(listLocations);
		assertEquals(2, listLocations.size());
	}
	
	@Test
	void save() {
		when(repo.save(any(Location.class))).thenReturn(location_1);
		
		Location savedLocation = service.save(location_1);
		
		assertNotNull(savedLocation);
		assertEquals(location_1.getAddress(), savedLocation.getAddress());
	}
	
	@Test
	void get() throws NotFoundException {
		Integer id = 1;
		
		when(repo.findById(anyInt())).thenReturn(Optional.of(location_1));
		
		Location location = service.get(id);
		
		assertNotNull(location);
	}
	
	@Test
	void delete() throws NotFoundException {
		Integer id = 1;
		
		when(repo.findById(anyInt())).thenReturn(Optional.of(location_1));
		
		doNothing().when(repo).deleteById(id);
		
		service.delete(id);
		
		verify(repo, times(1)).deleteById(id);
	}
}