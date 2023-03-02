package com.example.data.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.data.Solicitation;
import com.example.data.SolicitationStage;
import com.example.data.Owner;
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
	
	@NotNull(message = "Owner required")
	private Owner owner;
	private List<SolicitationStage> stages = new ArrayList<SolicitationStage>();
	
	public Solicitation transformToSolicitation() {
		Solicitation solicitation = new Solicitation(null, this.subject, this.description, null, this.state, this.owner, stages, null);
		return solicitation;
	}
}
