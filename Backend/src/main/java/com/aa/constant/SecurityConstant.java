package com.aa.constant;

public class SecurityConstant {

	public static final String[] PUBLIC_URLS = { "/auth/login", "/auth/register",  "/api/v1/cars/**", "/api/v1/users/**",
			 "/api/v1/rents/**", "/api/v1/locations/**"};
	public static final String AUTHORITIES = "authorities";
	public static final String ISSUER = "AA";
	public static final String AUDIENCE = "Car Rental";
	public static final long EXPIRATION_TIME = 432_000_000; // 5 days
	public static final String JWT_SECRET = "AASA^Ey$y7T^tYGqpe!U@8G*V*S5SmXyYC4zWSRx+9*HyR#)tvS#Xj#D#uzF)5Gp";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
	public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
	public static final String JWT_PREFIX = "Bearer ";
	public static final String JWT_HEADER = "JWT";

}
