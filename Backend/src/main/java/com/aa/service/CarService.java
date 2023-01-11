package com.aa.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aa.domain.Car;
import com.aa.enums.FuelType;
import com.aa.exception.CarNotFoundException;
import com.aa.repository.CarRepository;

@Service
public class CarService {

	@Autowired private CarRepository repo;
	
	public List<Car> listCars() {
		return repo.findAll();
	}
	
	public List<Car> findByBrandAndModel(String brand, String model) {
		return repo.findByBrandAndModel(brand, model);
	}
	
	public List<Car> findByFuelType(FuelType fuelType) {
		return repo.findByFuelType(fuelType);
	}
	
	public List<Car> findAvaibleCars(LocalDate startDate, LocalDate endDate) {
		return repo.findAvaibleCars(startDate, endDate);
	}
	
	public Car save(Car car) {
		return repo.save(car);
	}
	
	public Car get(Integer id) throws CarNotFoundException {
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new CarNotFoundException("Could not find any car with id: " + id);
		}
	}
	
	public void delete(Integer id) throws CarNotFoundException {
		Optional<Car> car = repo.findById(id);
		
		if (car.isEmpty()) {
			throw new CarNotFoundException("Could not find any car with id: " + id);
		}
		
		repo.deleteById(id);
	}
}