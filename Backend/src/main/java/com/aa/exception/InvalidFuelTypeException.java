package com.aa.exception;

public class InvalidFuelTypeException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidFuelTypeException(String s) {
		super(s);
	}
}
