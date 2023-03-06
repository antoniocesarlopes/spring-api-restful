package com.example.data.dto;

import com.example.data.entity.User;
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
public class UserSavedto {
	
	@NotBlank(message = "Name required")
	private String name;
	
	@Email(message = "Invalid email address")
	private String email;
	
	@Size(min = 7, max = 99, message = "Password must be between 7 and 99")
	private String password;
	
	@NotNull(message = "Role required")
	private Role role;
	
	public User transformToUser() {
		User user = new User(null, this.name, this.email, this.password, this.role, null, null);
	    return user;
	}

}
