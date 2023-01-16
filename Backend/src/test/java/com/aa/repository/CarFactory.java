package com.aa.repository;

import java.time.LocalDate;
import java.util.HashSet;

import com.aa.domain.Car;
import com.aa.domain.Location;
import com.aa.domain.Rent;
import com.aa.domain.User;
import com.aa.enums.FuelType;

public class CarFactory {

	public static Car BMWS3() {
		Car car = new Car("BMW", "S3", 2020, "Black", 5, FuelType.DIESEL, 100.0, false, new HashSet<>(), new Location(1));
		Rent rent = new Rent(LocalDate.now(), LocalDate.now().plusDays(5), car, new User(2));

		car.getRents().add(rent);

		return car;
	}

	public static Car AudiS8() {
		Car car = new Car("Audi", "S8", 2021, "Gray", 5, FuelType.DIESEL, 100.0, false, new HashSet<>(), new Location(1));
		Rent rent = new Rent(LocalDate.now(), LocalDate.now().plusDays(10), car, new User(2));

		car.getRents().add(rent);

		return car;
	}

	public static Car Mercedes() {
		Car car = new Car("Mercedes", "1970", 1970, "Black", 5, FuelType.GASOLINE, 250.0, false, new HashSet<>(),
				new Location(1));
		Rent rent = new Rent(LocalDate.now(), LocalDate.now().plusDays(2), car, new User(2));

		car.getRents().add(rent);
		
		return car;
	}

	public static Car AudiTT() {
		Car car = new Car("Audi", "TT", 2002, "Red", 2, FuelType.DIESEL, 50.0, false, new HashSet<>(), new Location(1));
		Rent rent = new Rent(LocalDate.now(), LocalDate.now().plusDays(12), car, new User(2));

		car.getRents().add(rent);
		
		return car;
	}

	public static Car Hyundai() {
		Car car = new Car("Hyundai", "Ioniq5", 2021, "Black", 5, FuelType.ELECTRIC, 125.0, false, new HashSet<>(), 
				new Location(1));
		Rent rent = new Rent(LocalDate.now(), LocalDate.now().plusDays(22), car, new User(2));

		car.getRents().add(rent);
		
		return car;
	}
	
	public static Car BMWX6() {
		Car car = new Car("BMW", "X6", 2019, "Blue", 5, FuelType.HYBRID, 200.0, false, new HashSet<>(), new Location(1));
		Rent rent = new Rent(LocalDate.now(), LocalDate.now().plusDays(30), car, new User(2));

		car.getRents().add(rent);
		
		return car;
	}
}