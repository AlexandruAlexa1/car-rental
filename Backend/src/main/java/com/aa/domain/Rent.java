package com.aa.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "rents")
public class Rent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	@NotNull(message = "The start date must not be null")
	@FutureOrPresent
	private LocalDate startDate;

	@Column(nullable = false)
	@NotNull(message = "The end date must not be null")
	@FutureOrPresent
	private LocalDate endDate;

	@ManyToOne
	@JoinColumn(name = "car_id")
//	@JsonManagedReference
	private Car car;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Rent() {
	}

	public Rent(Integer id) {
		this.id = id;
	}

	public Rent(LocalDate startDate, LocalDate endDate, Car car, User user) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.car = car;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Rent [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", car=" + car + ", user="
				+ user + "]";
	}
}
