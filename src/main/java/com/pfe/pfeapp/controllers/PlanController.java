package com.pfe.pfeapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.pfeapp.dto.PlanDto;
import com.pfe.pfeapp.dto.PlanResponseDto;
import com.pfe.pfeapp.entity.Plan;
import com.pfe.pfeapp.service.PlanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/plans")
public class PlanController {
	
	@Autowired
	private PlanService planService;
	
	
	@PostMapping
	public ResponseEntity<String> create(@Valid @RequestBody PlanDto planDto) {
		try {
			planService.createPlan(planDto);
			return ResponseEntity.ok("Plan crée correctement");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body((e.getMessage()));
		} catch(Exception e) {
			return ResponseEntity.status(500).body("Erreur Serveur!");
		}
		
	}
	
	@GetMapping
	public List<PlanResponseDto> getAll(){
		return planService.getAll();
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePlan(@PathVariable Long id) {
		try {
			planService.deletePlan(id);
			return ResponseEntity.ok("Plan supprimé");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage().toString());
		} catch (Exception e){
			return ResponseEntity.status(500).body("Erreur serveur!");
		}
	}
	

}
