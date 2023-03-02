package com.example.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.data.enums.SolicitationState;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity(name = "solicitation")
public class Solicitation implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 75, nullable = false)
	private String subject;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@Column(name = "creation_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(length = 12, nullable = false)
	@Enumerated(EnumType.STRING)
	private SolicitationState state;
	
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private Owner owner;
	
	@Getter(onMethod = @__({@JsonIgnore}))
	@OneToMany(mappedBy = "solicitation")
	private List<SolicitationStage> stages = new ArrayList<SolicitationStage>();
	
	@Getter(onMethod = @__({@JsonIgnore}))
	@OneToMany(mappedBy = "solicitation")
	private List<SolicitationFile> files = new ArrayList<SolicitationFile>();
}
