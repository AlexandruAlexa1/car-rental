package com.aa.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class CustomBadCredentialsException extends BadCredentialsException {
	
	private static final long serialVersionUID = 1L;

	public CustomBadCredentialsException(String msg) {
		super(msg);
	}

}
