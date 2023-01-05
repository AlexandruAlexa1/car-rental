package com.aa.domain;

import java.util.List;
import java.util.Set;

import com.aa.enums.FuelType;

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
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cars")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	@NotBlank(message = "Mark is required")
	private String brand;

	@Column(nullable = false)
	@NotBlank(message = "Model is required")
	private String model;

	@Column(nullable = false)
	@NotBlank(message = "Year is required")
	private Integer year;

	@Column(nullable = false)
	@NotBlank(message = "Color is required")
	private String color;
	
	@Column(nullable = false)
	@NotBlank(message = "Seat count is required")
	private Integer seatCount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotBlank(message = "Fuel type is required")
	private FuelType fuelType;

	@Min(value = 0, message = "The price cannot be less than 0")
	private Double pricePerDay;

	private boolean available;

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
	private Set<Image> images;

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
	private List<Rent> rents;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	public Car() {
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

	public List<Rent> getRents() {
		return rents;
	}

	public void setRents(List<Rent> rents) {
		this.rents = rents;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", brand=" + brand + ", model=" + model + ", year=" + year + ", color=" + color
				+ ", pricePerDay=" + pricePerDay + ", available=" + available + ", images=" + images + ", rents="
				+ rents + ", location=" + location + "]";
	}
	
}
