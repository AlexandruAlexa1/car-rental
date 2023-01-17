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

import com.aa.domain.Location;
import com.aa.exception.NotFoundException;
import com.aa.service.LocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationRestController {

	@Autowired private LocationService service;
	
	@GetMapping
	public ResponseEntity<List<Location>> listAll() {
		List<Location> listLocations = service.findAll();
		
		if (listLocations.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return new ResponseEntity<>(listLocations, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Location> add(@RequestBody @Valid Location location) {
		Location savedLocation = service.save(location);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedLocation.getId()).toUri();
		return ResponseEntity.created(uri).body(savedLocation);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Location> get(@PathVariable("id") Integer id) throws NotFoundException {
		return new ResponseEntity<>(service.get(id), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Location> update(@RequestBody @Valid Location location) {
		return new ResponseEntity<>(service.save(location), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws NotFoundException {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}















