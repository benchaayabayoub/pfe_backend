package com.pfe.pfeapp.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



	@Getter
	@Setter
	@AllArgsConstructor
	public class PackReponseDto {

		private Long id;
		private String nom;
		private String nomTeam;
		private Long teamId;
		private List<IpsReponseDto> ips;
	
	
	@Getter
	@Setter
	@AllArgsConstructor
	public static class HistoriqueDto{
		private String date;
		private long nbrSent;
	}
	
	
	
	@Getter
	@Setter
	@AllArgsConstructor
	public static class IpsReponseDto{
		private String nomServer;
		private String adresse;
		private long cumulSent;
		//private Long dernierNbrSent;
		//private List<HistoriqueDto> historique;
		private List<HistoriqueDto> historique;
	}
	
	
}
