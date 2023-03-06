package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.dto.SolicitationStageSavedto;
import com.example.data.entity.SolicitationStage;
import com.example.data.entity.User;
import com.example.service.SolicitationStageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/solicitation-stages")
@RequiredArgsConstructor
public class SolicitationStageController {

	private SolicitationStageService stageService;

	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Adds a new User",
	description = "Adds a new Solicitation Stage by passing in a JSON representation of the solicitation!",
	tags = {"Stage"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = SolicitationStage.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<SolicitationStage> save(@RequestBody @Valid SolicitationStageSavedto solicitationStagedto) {
		SolicitationStage solicitationStage = solicitationStagedto.transformToSolicitationStage();
		SolicitationStage createdSolicitationStage = stageService.save(solicitationStage);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdSolicitationStage);
	}

	@GetMapping(value = "/{id}",
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Find a Solicitation Stage", description = "Finds a Solicitation Stage passing your ID",
	tags = {"Stage"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = User.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<SolicitationStage> getById(@PathVariable(name = "id") Long id) {
		SolicitationStage stage = stageService.getById(id);
		return ResponseEntity.ok(stage);
	}

}
