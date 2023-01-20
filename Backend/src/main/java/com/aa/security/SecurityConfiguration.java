package com.aa.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aa.constant.SecurityConstant;
import com.aa.filter.JWTAccessDeniedHandler;
import com.aa.filter.JWTAuthorizationFilter;
import com.aa.filter.JWTForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired private UserDetailsService userDetailsService;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private JWTAuthorizationFilter jwtAuthorizationFilter;
	@Autowired private JWTAccessDeniedHandler accessDeniedHandler;
	@Autowired private JWTForbiddenEntryPoint jwtForbiddenEntryPoint;
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(List.of(authenticationProvider()));
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	 	http 
		 	.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers(SecurityConstant.PUBLIC_URLS).permitAll()
			.anyRequest().authenticated()
			.and()
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtForbiddenEntryPoint)
			.and()
			.authenticationManager(authenticationManager())
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	 	
	 	return http.build();
	}
}











