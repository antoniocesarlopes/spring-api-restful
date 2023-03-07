package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.dto.AccountCredentialsVO;
import com.example.data.dto.TokenVO;
import com.example.data.entity.User;
import com.example.exception.InvalidJwtAuthenticationException;
import com.example.service.AuthServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthServices authServices;
	
	@PostMapping(value = "/signin",
			consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Authenticates a user",
	description = "Authenticates a user and returns a token",
	tags = {"Auth"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = TokenVO.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)	
	public ResponseEntity<TokenVO> signin(@RequestBody AccountCredentialsVO data) {
		if (checkIfParamsIsNotNull(data))
			throw new InvalidJwtAuthenticationException("Invalid client request!");
		try {
			TokenVO token = authServices.signin(data);
			if (token == null) throw new InvalidJwtAuthenticationException("Invalid client request!");
			return ResponseEntity.ok(token);
		} catch (BadCredentialsException bce) {
			throw new InvalidJwtAuthenticationException(bce.getMessage());
		}
		
	}
	
	@PutMapping(value = "/refresh/{username}",
	produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Refresh token",
	description = "Refresh token for authenticated user and returns a token",
	tags = {"Auth"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = TokenVO.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)			
	public ResponseEntity<TokenVO> refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
		if (checkIfParamsIsNotNull(username, refreshToken)) {
			throw new InvalidJwtAuthenticationException("Invalid client request!");
		}
		TokenVO token = authServices.refreshToken(username, refreshToken);

		if (token == null) {
			throw new InvalidJwtAuthenticationException("Invalid client request!");
		}
		return ResponseEntity.ok(token);
	}

	private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
		return refreshToken == null || refreshToken.isBlank() ||
				username == null || username.isBlank();
	}

	private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank()
				 || data.getPassword() == null || data.getPassword().isBlank();
	}
}
