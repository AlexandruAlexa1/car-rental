package com.aa.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.aa.domain.HttpResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTForbiddenEntryPoint  extends Http403ForbiddenEntryPoint {

	private static final String ACCESS_DENIED = "Access denied. You do not have sufficient permissions to access this resource";

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException {
		HttpResponse httpResponse = new HttpResponse(HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value(), ACCESS_DENIED);
		JsonResponseHelper.writeJsonResponse(response, httpResponse);
	}

	
}
