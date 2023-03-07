package com.example.data.dto;

import com.example.data.enums.SolicitationState;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SolicitationUpdatedto {
	
	@NotBlank(message = "Subject required")
	private String subject;
	private String description;
	
	@NotNull(message = "State required")
	private SolicitationState state;
	
	@NotNull(message = "User required")
	private Long userId;
	
}
