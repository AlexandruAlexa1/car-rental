package com.aa.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aa.domain.AuthRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Valid AuthRequest authRequest) {
		return null;
	}
	
}
