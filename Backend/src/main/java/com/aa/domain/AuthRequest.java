package com.aa.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequest {

	@Email(message = "E-mail is not valid")
	@NotBlank(message = "E-mail is required")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;
	
	public AuthRequest() {}

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
}
