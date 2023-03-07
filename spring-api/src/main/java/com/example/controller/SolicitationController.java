package com.example.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.data.dto.SolicitationSavedto;
import com.example.data.dto.SolicitationUpdatedto;
import com.example.data.entity.Solicitation;
import com.example.data.entity.SolicitationFile;
import com.example.data.entity.SolicitationStage;
import com.example.data.entity.User;
import com.example.service.SolicitationFileService;
import com.example.service.SolicitationService;
import com.example.service.SolicitationStageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/solicitation")
@RequiredArgsConstructor
public class SolicitationController {
	
	private final SolicitationService solicitationService;
	private final SolicitationStageService stageService;
	private final SolicitationFileService fileService;
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Adds a new solicitation",
	description = "Adds a new solicitation by passing in a JSON representation of the solicitation!",
	tags = {"Solicitation"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = Solicitation.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<Solicitation> save(@RequestBody @Valid SolicitationSavedto solicitationdto) {
		Solicitation createdSolicitation = solicitationService.save(solicitationdto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdSolicitation);
	}
	
	@PreAuthorize("@accessManager.isSolicitationUser(#id)")
	@PutMapping(value = "/{id}",
	produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Update a Solicitation", description = "Updates a Solicitation",
	tags = {"Solicitation"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = Solicitation.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<Solicitation> update(@PathVariable(name = "id") Long id, @RequestBody @Valid SolicitationUpdatedto solicitationdto) {
		Solicitation updatedSolicitation = solicitationService.update(id, solicitationdto);
		return ResponseEntity.ok(updatedSolicitation);
	}
	
	@GetMapping(value = "/{id}",
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Find a Solicitation", description = "Finds a Solicitation",
	tags = {"Solicitation"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = Solicitation.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<Solicitation> getById(@PathVariable(name = "id") Long id) {
		Solicitation solicitation = solicitationService.getById(id);
		return ResponseEntity.ok(solicitation);
	}
	
	@GetMapping(
			produces = { MediaType.APPLICATION_JSON_VALUE })
		@Operation(summary = "Find all Solicitation", description = "Finds all Solicitation",
		tags = {"Solicitation"},
		responses = {
				@ApiResponse(description = "Success", responseCode = "200",
						content = @Content(schema = @Schema(implementation = Solicitation.class))
						),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
				@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
				)
	public ResponseEntity<Page<Solicitation>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "15") Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		Direction sortDirection = "DESC".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
		return ResponseEntity.ok(solicitationService.findAll(pageable));
	}
	
	@GetMapping(value = "/{id}/solicitation-stages",
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Find Solicitation Stage", description = "Find all Solicitation Stage by Solicitation id",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = SolicitationStage.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)	
	public ResponseEntity<Page<SolicitationStage>> findAllStagesById(
			@PathVariable(name = "id") Long id,
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "15") Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		Direction sortDirection = "DESC".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
		return ResponseEntity.ok(stageService.findAllBySolicitationId(id, pageable));
	}
	
	@GetMapping(value = "/{id}/files",
	produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Find Solicitation File", description = "Find all Solicitation File by Solicitation id",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = SolicitationFile.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)	
	public ResponseEntity<Page<SolicitationFile>> listAllFilesById(
			@PathVariable(name = "id") Long id,
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "15") Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		Direction sortDirection = "DESC".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
		return ResponseEntity.ok(fileService.listAllBySolicitationId(id, pageable));
	}
	
	@PostMapping(value = "/{id}/files",
			consumes = { "multipart/form-data" },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Upload a file",
	description = "Upload files to amazon s3",
	tags = {"Solicitation"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = SolicitationFile.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)	
	public ResponseEntity<List<SolicitationFile>> upload(@RequestParam("files") MultipartFile[] files, @PathVariable(name = "id") Long id) {
		List<SolicitationFile> solicitationFiles = fileService.upload(id, files);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(solicitationFiles);
	}

}
