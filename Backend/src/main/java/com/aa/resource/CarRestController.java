package com.aa.resource;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aa.domain.Car;
import com.aa.enums.FuelType;
import com.aa.exception.CarNotFoundException;
import com.aa.exception.InvalidFuelTypeException;
import com.aa.service.CarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cars")
public class CarRestController {

	@Autowired private CarService service;
	
	@GetMapping
	public ResponseEntity<List<Car>> listAll() {
		List<Car> listCars = service.listCars();
		
		if (listCars.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return new ResponseEntity<>(listCars, HttpStatus.OK);
	}
	
	@GetMapping("/brand-model")
	public ResponseEntity<List<Car>> findByBrandAndModel(@RequestParam String brand,
			@RequestParam String model) {
		List<Car> listCars = service.findByBrandAndModel(brand, model);
		
		if (listCars.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return new ResponseEntity<>(listCars, HttpStatus.OK);
	}
	
	@GetMapping("/fuel-type")
	public ResponseEntity<List<Car>> findByFuelType(@RequestParam String fuelType) {
		try {
			List<Car> listCars = service.findByFuelType(FuelType.valueOf(fuelType));
			
			if (listCars.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			
			return new ResponseEntity<>(listCars, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			throw new InvalidFuelTypeException("Invalid fuelType : " + fuelType);
		}
	}
	
	@GetMapping("/available")
	public ResponseEntity<List<Car>> findAvaibleCars(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		List<Car> listCars = service.findAvaibleCars(startDate, endDate);
		
		if (listCars.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return new ResponseEntity<>(listCars, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Car> save(@RequestBody @Valid Car car) {
		Car savedCar = service.save(car);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCar.getId()).toUri();
		
		return ResponseEntity.created(uri).body(savedCar);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Car> get(@PathVariable("id") Integer id) throws CarNotFoundException {
		return new ResponseEntity<>(service.get(id), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Car> update(@RequestBody @Valid Car car) {
		return new ResponseEntity<>(service.save(car), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws CarNotFoundException {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}