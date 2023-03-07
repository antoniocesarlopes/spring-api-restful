package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.data.dto.AccountCredentialsVO;
import com.example.data.dto.TokenVO;
import com.example.repository.UserRepository;
import com.example.security.JwtTokenProvider;

@Service
public class AuthServices {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;
	
	@SuppressWarnings("rawtypes")
	public TokenVO signin(AccountCredentialsVO data) {
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
			
			var user = repository.findByEmail(username);
			
			TokenVO tokenResponse = new TokenVO();
			if (user.isPresent()) {
				List<String> roles = new ArrayList<>();
				roles.add(user.get().getRole().name());
				tokenResponse = tokenProvider.createAccessToken(username, roles);
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			return tokenResponse;
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public TokenVO refreshToken(String username, String refreshToken) {
		var user = repository.findByEmail(username);
		
		TokenVO tokenResponse = new TokenVO();
		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		return tokenResponse;
	}
}
