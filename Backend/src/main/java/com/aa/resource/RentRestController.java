package com.aa.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aa.domain.Rent;
import com.aa.exception.NotFoundException;
import com.aa.service.RentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/rents")
public class RentRestController {

	@Autowired private RentService service;
	
	@GetMapping
	public ResponseEntity<List<Rent>> listAll() {
		List<Rent> listRents = service.findAll();
		
		if (listRents.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return new ResponseEntity<>(listRents, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Rent> save(@RequestBody @Valid Rent rent) {
		Rent savedRent = service.save(rent);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedRent.getId()).toUri();
		return ResponseEntity.created(uri).body(savedRent);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Rent> get(@PathVariable("id") Integer id) throws NotFoundException {
		return new ResponseEntity<>(service.get(id), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Rent> update(@RequestBody @Valid Rent rent) {
		return new ResponseEntity<>(service.save(rent), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws NotFoundException {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}














