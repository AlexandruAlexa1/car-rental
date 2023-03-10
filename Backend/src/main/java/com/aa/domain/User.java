package com.aa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 125, nullable = false, unique = true)
	@Email(message = "E-mail is not valid")
	@Length(min = 5, max = 125, message = "E-mail must have 5-125 characters")
	private String email;

	@Column(length = 64, nullable = false)
	@NotBlank(message = "Password is required")
	@Length(min = 5, max = 64, message = "Password must have 5-64 characters")
	private String password;

	@Column(length = 45, nullable = false)
	@NotBlank(message = "First name is required")
	@Length(min = 2, max = 45, message = "First name mush have between 5 and 45 characters")
	private String firstName;

	@Column(length = 45, nullable = false)
	@NotBlank(message = "Last name is required")
	@Length(min = 2, max = 45, message = "Last name mush have min 5 and max 45 characters")
	private String lastName;

	private Date joinDate;

	private Date lastLoginDate;

	private boolean isEnabled;

	private boolean isNotLocked;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	@ManyToMany
	@JoinTable(
		name = "users_roles", 
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	private List<Rent> rents = new ArrayList<>();

	public User() {}

	public User(Integer id) {
		this.id = id;
	}

	public User(String email, String password, String firstName, String lastName, Date joinDate, Date lastLoginDate,
			boolean isEnabled, boolean isNotLocked, Address address, Role role) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.joinDate = joinDate;
		this.lastLoginDate = lastLoginDate;
		this.isEnabled = isEnabled;
		this.isNotLocked = isNotLocked;
		this.address = address;
		this.roles.add(role);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isNotLocked() {
		return isNotLocked;
	}

	public void setNotLocked(boolean isNotLocked) {
		this.isNotLocked = isNotLocked;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Rent> getRents() {
		return rents;
	}

	public void setRents(List<Rent> rents) {
		this.rents = rents;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", joinDate=" + joinDate + ", lastLoginDate=" + lastLoginDate
				+ ", isEnabled=" + isEnabled + ", isNotLocked=" + isNotLocked + ", address=" + address + ", roles="
				+ roles + "]";
	}
}
