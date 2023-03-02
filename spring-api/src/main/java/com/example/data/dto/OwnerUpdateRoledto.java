package com.example.data.dto;

import com.example.data.enums.Role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OwnerUpdateRoledto {
	
	@NotNull(message = "Role required")
	private Role role;
}
