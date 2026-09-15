package com.pfe.pfeapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.pfeapp.entity.Entite;
import com.pfe.pfeapp.service.EntiteService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/entites")
public class EntiteController {

	
	
	
	@Autowired
	private EntiteService entiteService;
	
	@PostMapping
	public Entite create(@RequestBody Entite entite) {
		return entiteService.create(entite);
	}
	
	@PutMapping("/{id}")
	public Entite editById(@PathVariable Long id, @RequestBody Entite entite) {
		return entiteService.EditById(id, entite);
	}
	
	
	@GetMapping
	public List<Entite> getAll(){
		return entiteService.getAll();
	}
	
	@GetMapping("/{id}")
	public Entite getById(@PathVariable Long id) {
		return entiteService.getById(id);
	}
	


	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		entiteService.deleteEntiteWithTeams(id);
	}
	
	
}
