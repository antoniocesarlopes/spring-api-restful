package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.data.entity.Solicitation;
import com.example.data.enums.SolicitationState;
import com.example.exception.NotFoundException;
import com.example.repository.SolicitationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitationService {

	private final SolicitationRepository solicitationRepository;
	
	public Solicitation save(Solicitation solicitation) {
		solicitation.setState(SolicitationState.OPEN);
		solicitation.setCreationDate(new Date());
		
		Solicitation createdSolicitation = solicitationRepository.save(solicitation);
		
		return createdSolicitation;
	}
	
	public Solicitation update(Solicitation solicitation) {
		Solicitation updatedSolicitation = solicitationRepository.save(solicitation);
		return updatedSolicitation;
	}
	
	public Solicitation getById(Long id) {
		Optional<Solicitation> result = solicitationRepository.findById(id);
		return result.orElseThrow(()-> new NotFoundException("There are not solicitation with id = " + id));
	}
	
	public List<Solicitation> listAll() {
		List<Solicitation> solicitations = solicitationRepository.findAll();
		return solicitations;
	}
	
	public Page<Solicitation> findAll(Pageable pageable) {
		return solicitationRepository.findAll(pageable);
	}

	public List<Solicitation> listAllByUserId(Long userId) {
		List<Solicitation> solicitations = solicitationRepository.findAllByUserId(userId);
		return solicitations;
	}
	
	public Page<Solicitation> findAllByUserId(Long userId, Pageable pageable) {
		return solicitationRepository.findAllByUserId(userId, pageable);
	}
}
