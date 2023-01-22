package com.aa.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aa.domain.HttpResponse;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<HttpResponse> handleNotFoundException(NotFoundException ex) {
		HttpResponse respone = new HttpResponse(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), ex.getMessage());
		
		return new ResponseEntity<>(respone, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<HttpResponse> handlerInvalidFuelTypeException(InvalidFuelTypeException ex) {
		HttpResponse respone = new HttpResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		
		return new ResponseEntity<>(respone, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<HttpResponse> handleBadCredentialsException(BadCredentialsException ex) {
		HttpResponse response = new HttpResponse(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(),
				ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler
	public ResponseEntity<HttpResponse> handleAuthenticationCredentialsNotFoundException (AuthenticationCredentialsNotFoundException ex) {
		HttpResponse response = new HttpResponse(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler
	public ResponseEntity<HttpResponse> handleDuplicateEmailException (DuplicateEmailException ex) {
		HttpResponse response = new HttpResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		
		List<String> listErrors = new ArrayList<>();
		
		for (FieldError fieldError : fieldErrors) {
			String errorMessage = fieldError.getDefaultMessage();
			listErrors.add(errorMessage);
		}
		
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("timeStamp", new Date());
		response.put("status", status);
		response.put("message", listErrors);
		
		return new ResponseEntity<>(response, headers, status);
	}
	
	
}
