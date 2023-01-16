package com.aa.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aa.enums.FuelType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cars")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	@NotNull(message = "Brand is required")
	private String brand;

	@Column(nullable = false)
	@NotNull(message = "Model is required")
	private String model;

	@Column(nullable = false)
	@NotNull(message = "Year is required")
	private Integer year;

	@Column(nullable = false)
	@NotNull(message = "Color is required")
	private String color;

	@Column(nullable = false)
	@NotNull(message = "Seat count is required")
	private Integer seatCount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotNull(message = "Fuel type is required")
	private FuelType fuelType;

	@Min(value = 0, message = "The price cannot be less than 0")
	private Double pricePerDay;

	private boolean available;

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
	private Set<Image> images = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
	private List<Rent> rents = new ArrayList<>();

	public Car() {
	}

	public Car(Integer id) {
		this.id = id;
	}

	public Car(String brand, String model, Integer year, String color, Integer seatCount, FuelType fuelType,
			Double pricePerDay, boolean available, Set<Image> images, Location location) {
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.color = color;
		this.seatCount = seatCount;
		this.fuelType = fuelType;
		this.pricePerDay = pricePerDay;
		this.available = available;
		this.images = images;
		this.location = location;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	public FuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Rent> getRents() {
		return rents;
	}

	public void setRents(List<Rent> rents) {
		this.rents = rents;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", brand=" + brand + ", model=" + model + ", year=" + year + ", color=" + color
				+ ", seatCount=" + seatCount + ", fuelType=" + fuelType + ", pricePerDay=" + pricePerDay
				+ ", available=" + available + ", images=" + images + "]";
	}
}
