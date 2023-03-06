package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.data.entity.SolicitationStage;

@Repository
public interface SolicitationStageRepository extends JpaRepository<SolicitationStage, Long> {

	public List<SolicitationStage> findAllBySolicitationId(Long id);
	
	public Page<SolicitationStage> findAllBySolicitationId(Long id, Pageable pageable);

}
