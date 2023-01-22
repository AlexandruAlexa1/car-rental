package com.aa.filter;

import static com.aa.constant.SecurityConstant.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aa.utility.TokenVerifier;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The filter ensures that only the requests with the OPTIONS method
 * are allowed without checking the token
 * For the other requests, extract the token from the header and check it
 * if valid, sets the authentication in the security context
 * otherwise, clear the security context
 */
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	
	@Autowired private TokenVerifier tokenVerifier;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (isOptionsMethod(request)) {
			response.setStatus(HttpStatus.OK.value());
		} else if (!isPublicRoute(request)) {
			String token = getTokenFromHeader(request);
			String email = tokenVerifier.getSubject(token);
			
			tokenVerifier.verifyTokenAndBuildAuthentication(email, token, request);
		}
			
		filterChain.doFilter(request, response);
	}
	
	private boolean isPublicRoute(HttpServletRequest request) {
		for (String publicUrl : PUBLIC_URLS) {
			if (request.getServletPath().startsWith(publicUrl)) {
				return true;
			}
		}
		return false;
	}

	private String getTokenFromHeader(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (authorizationHeader == null || !authorizationHeader.startsWith(JWT_PREFIX)) {
			return null;
		}
		
		return authorizationHeader.substring(JWT_PREFIX.length());
	}

	private boolean isOptionsMethod(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD);
	}

}