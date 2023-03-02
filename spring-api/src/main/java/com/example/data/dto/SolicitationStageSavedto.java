package com.example.data.dto;

import com.example.data.Solicitation;
import com.example.data.SolicitationStage;
import com.example.data.Owner;
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
	
	@NotNull(message = "Owner required")
	private Owner owner;
	
	public SolicitationStage transformToSolicitationStage() {
		SolicitationStage stage = new SolicitationStage(null, description, null, state, solicitation, owner);
		return stage;
	}
}
