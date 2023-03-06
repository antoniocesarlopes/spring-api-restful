package com.example.data.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCredentialsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	
}