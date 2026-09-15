package com.pfe.pfeapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.pfeapp.dto.HistoriqueImportDto;
import com.pfe.pfeapp.service.HistoriqueEnvoiService;

@RestController
@RequestMapping("/api/historique")
public class HistoriqueEnvoiController {
	
	@Autowired
	private HistoriqueEnvoiService historiqueEnvoiService;
	
	@PostMapping("/import")
	public ResponseEntity<?> loadHistorique(@RequestBody HistoriqueImportDto historiqueImportDto) {
		try {
			historiqueEnvoiService.loadHistorique(historiqueImportDto.getNbrSentTxt());
			return ResponseEntity.ok("Historique importée avec succés");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
