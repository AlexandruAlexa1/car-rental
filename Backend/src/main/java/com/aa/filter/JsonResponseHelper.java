package com.aa.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.aa.domain.HttpResponse;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class JsonResponseHelper {

	public static void writeJsonResponse(HttpServletResponse response, HttpResponse httpResponse) throws StreamWriteException, DatabindException, IOException {
		setResponseHeader(response);
		writeResponse(response, httpResponse);
	}
	
	private static void writeResponse(HttpServletResponse response, HttpResponse httpResponse) throws StreamWriteException, DatabindException, IOException {
		ServletOutputStream outputStream = response.getOutputStream();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(outputStream, httpResponse);
		
		outputStream.flush();
	}

	private static void setResponseHeader(HttpServletResponse response) {
		response.setContentType("APPLICATION_JSON_VALUE");
		response.setStatus(HttpStatus.FORBIDDEN.value());
	}
}