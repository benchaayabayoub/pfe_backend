package com.pfe.pfeapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PackDto {

	
	@NotBlank(message = "Veuillez choisir un nom au pack !")
	private String nom;
	
	@NotNull(message = "Veuillez choisir le team correspondant !")
	private Long teamId;
	
	@NotBlank(message = "Veuillez entrez la liste: server:ips !")
	private String servIpList;
	
	@NotNull(message="veuillez choisir le plan correspondant!")
	private Long planId;
}
