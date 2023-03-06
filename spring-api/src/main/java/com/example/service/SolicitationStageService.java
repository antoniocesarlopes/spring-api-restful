package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.data.entity.SolicitationStage;
import com.example.data.enums.SolicitationState;
import com.example.exception.NotFoundException;
import com.example.repository.SolicitationRepository;
import com.example.repository.SolicitationStageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitationStageService {
	
	private final SolicitationStageRepository solicitationStageRepository;
	
	private final SolicitationRepository solicitationRepository;
	
	public SolicitationStage save(SolicitationStage stage) {
		stage.setRealizationDate(new Date());
		
		SolicitationStage createdStage = solicitationStageRepository.save(stage);
		
		Long solicitationId = stage.getSolicitation().getId();
		SolicitationState state = stage.getState();
		
		solicitationRepository.updateStatus(solicitationId, state);
		
		return createdStage;
	}
	
	public SolicitationStage getById(Long id) {
		Optional<SolicitationStage> result = solicitationStageRepository.findById(id);
		return result.orElseThrow(()-> new NotFoundException("There are not solicitation stage with id = " + id));
	}

	public List<SolicitationStage> listAllBySolicitationId(Long solicitationId) {
		List<SolicitationStage> stages = solicitationStageRepository.findAllBySolicitationId(solicitationId);
		return stages;
	}
	
	public Page<SolicitationStage> findAllBySolicitationId(Long solicitationId, Pageable pageable) {
		return solicitationStageRepository.findAllBySolicitationId(solicitationId, pageable);
	}
}
