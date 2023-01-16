package com.aa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.Rollback;

import com.aa.domain.Car;
import com.aa.domain.Location;
import com.aa.enums.FuelType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CarRepositoryTest {

	@Autowired private CarRepository repo;
	
	private Car bmws3;
	private Car audiS8;
	private Car mercedes;
	private Car audiTT;
	private Car hyundai;
	private Car bmwx6;
	
	@BeforeEach
	public void init() {
		bmws3 = CarFactory.BMWS3();
		audiS8 = CarFactory.AudiS8();
		mercedes = CarFactory.Mercedes();
		audiTT = CarFactory.AudiTT();
		hyundai = CarFactory.Hyundai();
		bmwx6 = CarFactory.BMWX6();
	}
	
	@Test
	@Description("It should save a car")
	void save() {
		Car savedCar = repo.save(bmws3);
		
		assertNotNull(savedCar);
		assertThat(savedCar.getId()).isGreaterThan(0);
	}
	
	@Test
	@Description("It should save more cars")
	void saveAll() {
		List<Car> savedCars = repo.saveAll(List.of(audiS8, mercedes, audiTT, hyundai, bmwx6));
		
		assertNotNull(savedCars);
		assertEquals(5, savedCars.size());
	}
	
	@Test
	void listAll() {
		List<Car> listCars = repo.findAll();
		
		assertNotNull(listCars);
		assertThat(listCars.size()).isGreaterThan(0);
		
		listCars.forEach(car -> System.out.println(car));
	}
	
	@Test
	void testFindByBrandAndModel() {
		String brand = "BMW";
		String model = "S3";
		
		List<Car> listCars = repo.findByBrandAndModel(brand, model);
		
		assertNotNull(listCars);
		assertThat(listCars.size()).isGreaterThan(0);
		
		for (Car car : listCars) {
			assertEquals(brand, car.getBrand());
			assertEquals(model, car.getModel());
		}
	}
	
	@Test
	void testFindByFuelType() {
		List<Car> listCars = repo.findByFuelType(FuelType.DIESEL);
		
		assertNotNull(listCars);
		assertThat(listCars.size()).isGreaterThan(0);
	}
	
//	@Test
//	void testFindAvaibleCars() {
//		LocalDate startDate = LocalDate.now().plusDays(20);
//		LocalDate endDate = LocalDate.now().plusDays(35);
//		
//		List<Car> listCars = repo.findAvaibleCars(startDate, endDate);
//		
//		assertNotNull(listCars);
//		assertThat(listCars.size()).isGreaterThan(0);
//		
//		listCars.forEach(car -> System.err.println(car));
//	}
	
	@Test
	void get() {
		Integer id = 31;
		
		Car findedCar = repo.findById(id).get();
		
		assertNotNull(findedCar);
	}
	
	@Test
	void update() {
		Car findedCar = repo.findById(1).get();
		findedCar.setLocation(new Location(1));
		
		Car updatedCar = repo.save(findedCar);
		
		assertNotNull(updatedCar);
	}
	
	@Test
	void delete() {
		Integer id = 51;
		
		repo.deleteById(id);
		
		Optional<Car> findedCar = repo.findById(id);
		
		assertThat(findedCar.isEmpty());
	}
}