package com.aa.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String address;
	
	private String phoneNumber;
	
	private String email;
	
	@OneToMany(mappedBy = "location")
	private List<Car> cars;
	
	public Location() {}
	
	public Location(Integer id) {
		this.id = id;
	}

	public Location(String address, String phoneNumber, String email, List<Car> cars) {
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.cars = cars;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", address=" + address + ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", cars=" + cars + "]";
	}
	
}
