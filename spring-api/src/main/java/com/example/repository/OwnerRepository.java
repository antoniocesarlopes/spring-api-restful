package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.data.Owner;
import com.example.data.enums.Role;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long>, JpaSpecificationExecutor<Owner> {

	@Query("SELECT o FROM owner o WHERE email = ?1 AND password = ?2")
	//poderia ser substituído por findByEmailAndPassword
	public Optional<Owner> login(String email, String password);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("UPDATE owner SET role = ?2 WHERE id = ?1")
	public int updateRole(Long id, Role role);
	
	public Optional<Owner> findByEmail(String email);
	
}
