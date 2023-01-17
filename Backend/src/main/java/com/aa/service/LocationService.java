package com.aa.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aa.domain.Location;
import com.aa.exception.NotFoundException;
import com.aa.repository.LocationRepository;

@Service
public class LocationService {

	@Autowired private LocationRepository repo;
	
	public List<Location> findAll() {
		return repo.findAll();
	}
	
	public Location save(Location location) {
		return repo.save(location);
	}
	
	public Location get(Integer id) throws NotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Could not find any location with id: " + id);
		}
	}
	
	public void delete(Integer id) throws NotFoundException {
		Optional<Location> location = repo.findById(id);
		
		if (location.isEmpty()) {
			throw new NotFoundException("Could not find any location with id: " + id);
		}
		
		repo.deleteById(id);
	}
}
