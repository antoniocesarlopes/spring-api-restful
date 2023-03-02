package com.example.data.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.data.Solicitation;
import com.example.data.SolicitationStage;
import com.example.data.Owner;
import com.example.data.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OwnerSavedto {
	
	@NotBlank(message = "Name required")
	private String name;
	
	@Email(message = "Invalid email address")
	private String email;
	
	@Size(min = 7, max = 99, message = "Password must be between 7 and 99")
	private String password;
	
	@NotNull(message = "Role required")
	private Role role;
	
	private List<Solicitation> solicitations = new ArrayList<Solicitation>();
	private List<SolicitationStage> stages = new ArrayList<SolicitationStage>();
	
	public Owner transformToOwner() {
		Owner owner = new Owner(null, this.name, this.email, this.password, this.role, this.solicitations, this.stages);
	    return owner;
	}

}
