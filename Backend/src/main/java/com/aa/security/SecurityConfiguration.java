package com.aa.security;

import static com.aa.constant.SecurityConstant.PUBLIC_URLS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
		 	.csrf().disable().cors().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeHttpRequests()
			.requestMatchers(PUBLIC_URLS).permitAll()
			.requestMatchers("/api/v1/users/**").hasAuthority("ADMIN")
			.requestMatchers(HttpMethod.GET, "/api/v1/locations/**").hasAnyAuthority("ADMIN", "USER")
			.requestMatchers(HttpMethod.POST, "/api/v1/locations").hasAuthority("ADMIN")
			.requestMatchers(HttpMethod.PUT, "/api/v1/locations").hasAuthority("ADMIN")
			.requestMatchers(HttpMethod.DELETE, "/api/v1/locations/**").hasAuthority("ADMIN")
			.requestMatchers(HttpMethod.GET, "/api/v1/cars/**").hasAnyAuthority("ADMIN", "USER")
			.requestMatchers(HttpMethod.POST, "/api/v1/cars").hasAuthority("ADMIN")
			.requestMatchers(HttpMethod.PUT, "/api/v1/cars").hasAuthority("ADMIN")
			.requestMatchers(HttpMethod.DELETE, "/api/v1/cars/**").hasAuthority("ADMIN")
			.anyRequest().authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtForbiddenEntryPoint)
			.and()	
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
			.and()
			.authenticationManager(authenticationManager())
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	 	
	 	return http.build();
	}
}