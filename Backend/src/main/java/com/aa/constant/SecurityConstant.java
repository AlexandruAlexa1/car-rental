package com.aa.constant;

public class SecurityConstant {

	public static final String[] PUBLIC_URLS = { "/auth/login", "/auth/logout", "/auth/register" };
	public static final String AUTHORITIES = "authorities";
	public static final String ISSUER = "AA";
	public static final String AUDIENCE = "Car Rental";
	public static final long EXPIRATION_TIME = 432_000_000; // 5 days
	public static final String JWT_SECRET = "AASA^Ey$y7T^tYGqpe!U@8G*V*S5SmXyYC4zWSRx+9*HyR#)tvS#Xj#D#uzF)5Gp";
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";

}
