package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.data.entity.Solicitation;
import com.example.data.enums.SolicitationState;

@Repository
public interface SolicitationRepository extends JpaRepository<Solicitation, Long> {
		
	public List<Solicitation> findAllByUserId(Long id);
	
	public Page<Solicitation> findAllByUserId(Long id, Pageable pageable);
	
	@Transactional(readOnly = false)
	@Modifying
	@Query("UPDATE solicitation SET state = ?2 WHERE id = ?1")
	public int updateStatus(Long id, SolicitationState state);
}
