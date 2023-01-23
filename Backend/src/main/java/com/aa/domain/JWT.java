package com.aa.domain;

import java.time.Instant;

import static com.aa.constant.SecurityConstant.*;

public class JWT {

	private String jwt;
	
	private Instant expirantionDate;
	
	public JWT() {
		
	}

	public JWT(String jwt) {
		this.jwt = jwt;
		this.expirantionDate = Instant.now().plusMillis(EXPIRATION_TIME);
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public Instant getExpirantionDate() {
		return expirantionDate;
	}

	public void setExpirantionDate(Instant expirantionDate) {
		this.expirantionDate = expirantionDate;
	}

	@Override
	public String toString() {
		return "JWT [jwt=" + jwt + ", expirantionDate=" + expirantionDate + "]";
	}
}