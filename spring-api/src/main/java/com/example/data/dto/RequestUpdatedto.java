package com.example.data.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.data.Request;
import com.example.data.RequestStage;
import com.example.data.User;
import com.example.data.enums.RequestState;

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
public class RequestUpdatedto {
	@NotBlank(message = "Subject required")
	private String subject;
	private String description;
	
	@NotNull(message = "State required")
	private RequestState state;
	
	@NotNull(message = "Owner required")
	private User owner;
	private List<RequestStage> stages = new ArrayList<RequestStage>();
	
	public Request transformToRequest() {
		Request request = new Request(null, this.subject, this.description, null, this.state, this.owner, stages, null);
		return request;
	}
}
