package com.aa.utility;

import java.time.Instant;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.aa.domain.UserPrincipal;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import static com.aa.constant.SecurityConstant.*;

@Component
public class TokenProvider {
	
	public String generateJWT(UserPrincipal userPrincipal) {
		String[] claims = getClaims(userPrincipal);
		
		return createToken(userPrincipal, claims);
	}

	private String createToken(UserPrincipal userPrincipal, String[] claims) {
		return JWT.create()
				.withSubject(userPrincipal.getUsername())
				.withArrayClaim(AUTHORITIES, claims)
				.withIssuer(ISSUER)
				.withAudience(AUDIENCE)
				.withIssuedAt(Instant.now())
				.withExpiresAt(Instant.now().plusMillis(EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(JWT_SECRET));
	}

	private String[] getClaims(UserPrincipal userPrincipal) {
		return userPrincipal.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.toArray(String[]::new);
	}
}
