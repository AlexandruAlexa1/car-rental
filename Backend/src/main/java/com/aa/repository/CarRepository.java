package com.aa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aa.domain.Car;
import com.aa.enums.FuelType;

public interface CarRepository extends JpaRepository<Car, Integer> {

	public List<Car> findByBrandAndModel(String brand, String model);

	public List<Car> findByFuelType(FuelType fuelType);

	@Query("SELECT c FROM Car c WHERE c NOT IN (SELECT r.car FROM Rent r WHERE (r.startDate BETWEEN :startDate AND :endDate) "
			+ "OR (r.endDate BETWEEN :startDate AND :endDate))")
	public List<Car> findAvaibleCars(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
