package com.example.data.dto;

import com.example.data.Request;
import com.example.data.RequestStage;
import com.example.data.User;
import com.example.data.enums.RequestState;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestStageSavedto {
	private String description;
	
	@NotNull(message = "State required")
	private RequestState state;
	
	@NotNull(message = "Request required")
	private Request request;
	
	@NotNull(message = "Owner required")
	private User owner;
	
	public RequestStage transformToRequestStage() {
		RequestStage stage = new RequestStage(null, description, null, state, request, owner);
		return stage;
	}
}
