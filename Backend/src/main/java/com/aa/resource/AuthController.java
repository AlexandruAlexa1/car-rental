package com.aa.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aa.constant.SecurityConstant;
import com.aa.domain.AuthRequest;
import com.aa.domain.JWT;
import com.aa.domain.User;
import com.aa.domain.UserPrincipal;
import com.aa.exception.DuplicateEmailException;
import com.aa.exception.NotFoundException;
import com.aa.service.UserService;
import com.aa.utility.TokenProvider;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private TokenProvider tokenProvider;
	@Autowired private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JWT> login(@RequestBody @Valid AuthRequest request) throws NotFoundException {
		authenticateUser(request.getEmail(), request.getPassword());
		
		String jwt = tokenProvider.generateJWT(new UserPrincipal(userService.findByEmail(request.getEmail())));
		
		return new ResponseEntity<>(new JWT(jwt), generateAuthorizationHeader(jwt), HttpStatus.OK);
	}

	private HttpHeaders generateAuthorizationHeader(String jwt) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_HEADER, jwt);
		
		return headers;
	}

	private void authenticateUser(String email, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody @Valid User user) throws DuplicateEmailException {
		return new ResponseEntity<>(userService.register(user), HttpStatus.OK);
	}
}
