package com.aa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aa.domain.Car;
import com.aa.enums.FuelType;
import com.aa.repository.CarFactory;
import com.aa.repository.CarRepository;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

	@InjectMocks
	private CarService service;
	
	@Mock
	private CarRepository repo;
	
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
	void testListCars() {
		when(repo.findAll()).thenReturn(List.of(bmws3, audiS8, mercedes, audiTT, hyundai, bmwx6));
		
		List<Car> listCars = service.listCars();
		
		assertNotNull(listCars);
		assertEquals(6, listCars.size());
	}
	
	@Test
	void testFindByBrandAndModel() {
		String brand = "BMW";
		String model = "X6";
		
		when(repo.findByBrandAndModel(brand, model)).thenReturn(List.of(bmwx6));
		
		List<Car> listCars = service.findByBrandAndModel(brand, model);
		
		assertNotNull(listCars);
		assertEquals(1, listCars.size());
	}
	
	@Test
	void testFindByFuelType() {
		FuelType fuelType = FuelType.HYBRID;
		
		when(repo.findByFuelType(fuelType)).thenReturn(List.of(hyundai));
		
		List<Car> listCars = service.findByFuelType(fuelType);
		
		assertNotNull(listCars);
		assertEquals(1, listCars.size());
	}
	
	@Test
	void testFindAvaibleCars() {
		LocalDate startDate = LocalDate.now().plusDays(20);
		LocalDate endDate = LocalDate.now().plusDays(35);
		
		when(repo.findAvaibleCars(startDate, endDate)).thenReturn(List.of(bmws3, audiS8, mercedes, audiTT));
		
		List<Car> listCars = service.findAvaibleCars(startDate, endDate);
		
		assertNotNull(listCars);
		assertEquals(4, listCars.size());
	}
}