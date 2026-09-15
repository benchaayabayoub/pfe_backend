package com.pfe.pfeapp.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter  @Setter
public class PlanResponseDto {
	
	private Long id;
	private String nom;
	private List<PlanJourDto> jours;
	
}
