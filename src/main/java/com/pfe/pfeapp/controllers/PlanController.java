package com.pfe.pfeapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public PlanResponseDto create(@Valid @RequestBody PlanDto planDto) {
		return planService.createPlan(planDto);
	}
	
	@GetMapping
	public List<PlanResponseDto> getAll(){
		return planService.getAll();
	}
	
	
	@DeleteMapping("/{id}")
	public void deletePlan(@PathVariable Long id) {
		planService.deletePlan(id);
	}
	

}
