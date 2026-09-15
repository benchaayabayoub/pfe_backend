package com.pfe.pfeapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class UsernameDto {
	
	
	@NotBlank(message="Veuillez choisir un username!")
	private String usernameAutorise;
}
