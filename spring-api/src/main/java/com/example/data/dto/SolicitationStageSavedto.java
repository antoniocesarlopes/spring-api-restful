package com.example.data.dto;

import com.example.data.entity.User;
import com.example.data.entity.Solicitation;
import com.example.data.entity.SolicitationStage;
import com.example.data.enums.SolicitationState;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SolicitationStageSavedto {
	private String description;
	
	@NotNull(message = "State required")
	private SolicitationState state;
	
	@NotNull(message = "Solicitation required")
	private Solicitation solicitation;
	
	@NotNull(message = "User required")
	private User user;
	
	public SolicitationStage transformToSolicitationStage() {
		SolicitationStage stage = new SolicitationStage(null, description, null, state, solicitation, user);
		return stage;
	}
}
