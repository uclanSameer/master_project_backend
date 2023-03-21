package com.example.neighbour.configuration.security.authentication;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class JwtAuthentication extends UsernamePasswordAuthenticationToken{

	private final String jwt;
	
	public JwtAuthentication(Object principal, Object credentials, String jwt) {
		super(principal, credentials);
		this.jwt = jwt;

	}
	
	public JwtAuthentication(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, String jwt) {
		super(principal, credentials, authorities);
		this.jwt = jwt;
	}



}
