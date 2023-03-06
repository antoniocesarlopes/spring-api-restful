package com.example.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.data.entity.User;
import com.example.exception.NotFoundException;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	private final PagedResourcesAssembler<User> assembler;
	
	public User save(User user) {
		String hash = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(hash);
		
		User createdUser = userRepository.save(user);
		return createdUser;
	}
	
	public User update(User user) {
		String hash = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(hash);
		
		User updatedUser = userRepository.save(user);
		return updatedUser;
	}
	
	public User getById(Long id) {
		Optional<User> result = userRepository.findById(id);
		
		return result.orElseThrow(()-> new NotFoundException("There are not user with id = " + id));
	}
	
	public List<User> listAll() {
		List<User> users = userRepository.findAll();
		return users;
	}
	
	public Page<User> findAll(Pageable pageable) {
		
		return userRepository.findAll(pageable);
	}

	public int updateRole(User user) {
		return userRepository.updateRole(user.getId(), user.getRole());
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> result = userRepository.findByEmail(userName);
		
		if(!result.isPresent()) throw new UsernameNotFoundException("Dosen't exist user with email = " + userName);
		
		User user = result.get();
		
		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
		
		org.springframework.security.core.userdetails.User userSpring = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
		
		return userSpring;
	}
}
