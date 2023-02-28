package com.example.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLogindto {
	
	@Email(message = "Invalid email address")
	private String email;
	
	@NotBlank(message = "Password required")
	private String password;
}
