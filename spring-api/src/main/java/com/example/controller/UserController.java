package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.data.dto.UserSavedto;
import com.example.data.dto.UserUpdateRoledto;
import com.example.data.dto.UserUpdatedto;
import com.example.data.entity.Solicitation;
import com.example.data.entity.User;
import com.example.service.SolicitationService;
import com.example.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final SolicitationService solicitationService;

	@Secured({ "ROLE_ADMINISTRATOR" })
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Adds a new User",
	description = "Adds a new User by passing in a JSON representation of the user!",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = User.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<User> save(@RequestBody @Valid UserSavedto userdto) {
		User userToSave = userdto.transformToUser();
		User createdUser = userService.save(userToSave);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PreAuthorize("@accessManager.isUser(#id)")
	@PutMapping(value = "/{id}",
		produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Update a User", description = "Updates a User",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = User.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<User> update(@PathVariable(name = "id") Long id, @RequestBody @Valid UserUpdatedto userdto) {
		User user = userdto.transformToUser();
		user.setId(id);
		User updatedUser = userService.update(user);
		return ResponseEntity.ok(updatedUser);

	}

	@Secured({ "ROLE_ADMINISTRATOR" })
	@GetMapping(value = "/{id}",
		produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Find a User", description = "Finds a User",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = User.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<User> getById(@PathVariable("id") Long id) {
		User user = userService.getById(id);
		return ResponseEntity.ok(user);
	}

	@GetMapping(
		produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Find all Users", description = "Finds all Users",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = User.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<Page<User>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "15") Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){

		Direction sortDirection = "DESC".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
		return ResponseEntity.ok(userService.findAll(pageable));
	}

	@GetMapping(value = "/{id}/solicitations",
		produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "Find solicitations", description = "Find all solicitations by user id",
	tags = {"User"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = Solicitation.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<Page<Solicitation>> listAllSolicitationsById(
			@PathVariable(name = "id") Long id,
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "15") Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Direction sortDirection = "DESC".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
		return ResponseEntity.ok(solicitationService.findAllByUserId(id, pageable));
	}

	@Secured({ "ROLE_ADMINISTRATOR" })
	@PatchMapping(value = "/role/{id}",
		produces = { MediaType.APPLICATION_JSON_VALUE  })	
	@Operation(summary = "Update role", description = "Changes a User Role by your ID", tags = {"User"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200",
					content = @Content(schema = @Schema(implementation = Integer.class))
					),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	}
			)
	public ResponseEntity<Integer> updateRole(@PathVariable(name = "id") Long id, @RequestBody @Valid UserUpdateRoledto userdto) {
		User user = new User();
		user.setId(id);
		user.setRole(userdto.getRole());

		Integer rows = userService.updateRole(user);

		return ResponseEntity.ok(rows);
	}
}
