package com.example.data;

import java.io.Serializable;
import java.util.Date;

import com.example.data.enums.SolicitationState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "solicitation_stage")
public class SolicitationStage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@Column(name = "realization_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date realizationDate;
	
	@Column(length = 12, nullable = false)
	@Enumerated(EnumType.STRING)
	private SolicitationState state;
	
	@ManyToOne
	@JoinColumn(name = "solicitation_id", nullable = false)
	private Solicitation solicitation;
	
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private Owner owner;
}
