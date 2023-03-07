package com.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.data.dto.SolicitationSavedto;
import com.example.data.dto.SolicitationUpdatedto;
import com.example.data.entity.Solicitation;
import com.example.data.enums.SolicitationState;
import com.example.exception.NotFoundException;
import com.example.repository.SolicitationRepository;
import com.example.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitationService {

	private final SolicitationRepository solicitationRepository;
	
	private final UserRepository userRepository;
	
	public Solicitation save(SolicitationSavedto solicitationSavedto) {
		
		Solicitation solicitation = mapDtoToEntity(solicitationSavedto);
		
		solicitation.setState(SolicitationState.OPEN);
		solicitation.setCreationDate(LocalDateTime.now());
		
		return solicitationRepository.save(solicitation);
	}
	
	public Solicitation update(Long id, @Valid SolicitationUpdatedto solicitationdto) {
		
		Optional<Solicitation> solicitationOpt = solicitationRepository.findById(id);
		
		if(solicitationOpt.isPresent()) {
			Solicitation solicitation = solicitationOpt.get();
			
			userRepository.findById(solicitation.getUser().getId())
			.ifPresentOrElse( user -> {
				solicitation.setUser(user);
			}, () -> {
				throw new NoSuchElementException();
			});

			solicitation.setSubject(solicitationdto.getSubject());
			solicitation.setDescription(solicitationdto.getDescription());
			solicitation.setState(solicitationdto.getState());

			return solicitationRepository.save(solicitation);
		}else {
			throw new NoSuchElementException();
		}

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
	
	private Solicitation mapDtoToEntity(SolicitationSavedto solicitationSavedto) {
		Solicitation solicitation = new Solicitation();

		userRepository.findById(solicitationSavedto.getUserId())
		.ifPresentOrElse( user -> {
			solicitation.setUser(user);
		}, () -> {
			throw new RuntimeException();
		});

		solicitation.setSubject(solicitationSavedto.getSubject());
		solicitation.setDescription(solicitationSavedto.getDescription());
		solicitation.setCreationDate(LocalDateTime.now());

		return solicitation;
	}
	
}
