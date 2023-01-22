package com.aa.utility;

import static com.aa.constant.SecurityConstant.AUTHORITIES;
import static com.aa.constant.SecurityConstant.ISSUER;
import static com.aa.constant.SecurityConstant.JWT_SECRET;
import static com.aa.constant.SecurityConstant.TOKEN_CANNOT_BE_VERIFIED;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class TokenVerifier {

	public String getSubject(String token) {
		return verifyToken().verify(token).getSubject();
	}

	private JWTVerifier verifyToken() {
		try {
			Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
			
			return JWT.require(algorithm).withIssuer(ISSUER).build();
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
		}
	}
	
	public boolean isTokenValid(String email, String token) {
		if (email == null || email.isEmpty() || token == null || token.isEmpty()) {
			throw new IllegalArgumentException("E-mail and token are required");
		}
		
		return !email.isEmpty() && !isTokenExpired(verifyToken(), token);
	}

	private boolean isTokenExpired(JWTVerifier verifyToken, String token) {
		Date expiresAt = verifyToken.verify(token).getExpiresAt();
		
		return expiresAt.before(new Date());
	}
	
	public Set<GrantedAuthority> getGrantedAuthorities(String token) {
		if (token == null || token.isEmpty()) {
			throw new IllegalArgumentException("Token is required");
		}
		
		String[] claims = getClaimsFromToken(token);
		
		return Arrays.stream(claims)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}

	private String[] getClaimsFromToken(String token) {
		JWTVerifier verifyToken = verifyToken();
		
		return verifyToken.verify(token).getClaim(AUTHORITIES).asArray(String.class);
	}
	
	public Authentication buildAuthenticationToken(String email, Set<GrantedAuthority> grantedAuthorities, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(email, request, grantedAuthorities);
		
		setAuthenticationDetails(authenticationToken, request);
		
		return authenticationToken;
	}

	private void setAuthenticationDetails(UsernamePasswordAuthenticationToken authenticationToken,
			HttpServletRequest request) {
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	}
	
	public void verifyTokenAndBuildAuthentication(String email, String token, HttpServletRequest request) {
		if (isTokenValid(email, token)) {
			Set<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(token);
			
			Authentication authentication = buildAuthenticationToken(email, grantedAuthorities, request);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			SecurityContextHolder.clearContext();
		}
	}
}