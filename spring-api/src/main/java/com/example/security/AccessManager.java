package com.example.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.data.entity.Solicitation;
import com.example.data.entity.User;
import com.example.exception.NotFoundException;
import com.example.repository.UserRepository;
import com.example.service.SolicitationService;

@Component("accessManager")
public class AccessManager {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SolicitationService solicitationService;
	
	public boolean isOwner(Long id) {
		String email = (String) getUserNameFromContext();
		Optional<User> result = userRepository.findByEmail(email);
		
		if(!result.isPresent()) throw new NotFoundException("There are not user with email = " + email);
		
		User user = result.get();
		
		return user.getId() == id;
	}
	
	public boolean isSolicitationUser(Long id) {
		String email = (String) getUserNameFromContext();
		Optional<User> result = userRepository.findByEmail(email);
		
		if(!result.isPresent()) throw new NotFoundException("There are not user with email = " + email);
		
		User user = result.get();
		
		Solicitation solicitation = solicitationService.getById(id);
		
		return user.getId() == solicitation.getUser().getId();
		
	}

	private String getUserNameFromContext() {
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(user != null) {
			return user.getUsername();
		}else {
			throw new NotFoundException("There are not user in context");
		}
		
	}

}
