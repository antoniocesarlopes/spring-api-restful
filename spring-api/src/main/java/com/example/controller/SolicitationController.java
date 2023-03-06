package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
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

import com.example.data.dto.SolicitationSavedto;
import com.example.data.dto.SolicitationUpdatedto;
import com.example.data.entity.Solicitation;
import com.example.data.entity.SolicitationStage;
import com.example.service.SolicitationFileService;
import com.example.service.SolicitationService;
import com.example.service.SolicitationStageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/solicitation")
@RequiredArgsConstructor
public class SolicitationController {
	
	private final SolicitationService solicitationService;
	private final SolicitationStageService stageService;
	private final SolicitationFileService fileService;
	
	@PostMapping
	public ResponseEntity<Solicitation> save(@RequestBody @Valid SolicitationSavedto solicitationdto) {
		Solicitation solicitation = solicitationdto.transformToSolicitation();
		Solicitation createdSolicitation = solicitationService.save(solicitation);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdSolicitation);
	}
	
	@PreAuthorize("@accessManager.isRequestUser(#id)")
	@PutMapping("/{id}")
	public ResponseEntity<Solicitation> update(@PathVariable(name = "id") Long id, @RequestBody @Valid SolicitationUpdatedto solicitationdto) {
		Solicitation solicitation = solicitationdto.transformToSolicitation();
		solicitation.setId(id);
		
		Solicitation updatedSolicitation = solicitationService.update(solicitation);
		return ResponseEntity.ok(updatedSolicitation);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Solicitation> getById(@PathVariable(name = "id") Long id) {
		Solicitation solicitation = solicitationService.getById(id);
		return ResponseEntity.ok(solicitation);
	}
	
	@GetMapping
	public ResponseEntity<Page<Solicitation>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "15") Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		Direction sortDirection = "DESC".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
		return ResponseEntity.ok(solicitationService.findAll(pageable));
	}
	
	@GetMapping("/{id}/solicitation-stages")
	public ResponseEntity<Page<SolicitationStage>> findAllStagesById(
			@PathVariable(name = "id") Long id,
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "15") Integer size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
		
		Direction sortDirection = "DESC".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
		return ResponseEntity.ok(stageService.findAllBySolicitationId(id, pageable));
	}
	
//	@GetMapping("/{id}/files")
//	public ResponseEntity<PageModel<SolicitationFile>> listAllFilesById(
//			@PathVariable(name = "id") Long id,
//			@RequestParam Map<String, String> params) {
//		PageRequestModel pr = new PageRequestModel(params);
//		PageModel<SolicitationFile> pm = fileService.listAllBySolicitationId(id, pr);
//		return ResponseEntity.ok(pm);
//	}
	
//	@PostMapping("/{id}/files")
//	public ResponseEntity<List<SolicitationFile>> upload(@RequestParam("files") MultipartFile[] files, @PathVariable(name = "id") Long id) {
//		List<SolicitationFile> solicitationFiles = fileService.upload(id, files);
//		
//		return ResponseEntity.status(HttpStatus.CREATED).body(solicitationFiles);
//	}

}
