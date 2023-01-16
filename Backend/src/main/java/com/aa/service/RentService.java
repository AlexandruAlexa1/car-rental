package com.aa.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aa.domain.Rent;
import com.aa.exception.NotFoundException;
import com.aa.repository.RentRepository;

@Service
public class RentService {

	@Autowired private RentRepository repo;
	
	public List<Rent> findAll() {
		return repo.findAll();
	}
	
	public Rent save(Rent rent) {
		return repo.save(rent);
	}
	
	public Rent get(Integer id) throws NotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Could not find any rent with id: " + id);
		}
	}
	
	public void delete(Integer id) throws NotFoundException {
		Optional<Rent> rent = repo.findById(id);
		
		if (rent.isEmpty()) {
			throw new NotFoundException("Could not find any rent with id: " + id);
		}
		
		repo.deleteById(id);
	}
}
