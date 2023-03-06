package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.data.entity.SolicitationFile;

@Repository
public interface SolicitationFileRepository extends JpaRepository<SolicitationFile, Long> {

	public Page<SolicitationFile> findAllBySolicitationId(Long id, Pageable pageable);
}
