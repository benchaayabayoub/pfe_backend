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

import com.pfe.pfeapp.entity.Team;
import com.pfe.pfeapp.service.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

	@Autowired
	private TeamService teamService;
	
	
	@PostMapping
	public Team create(@RequestBody Team team) {
		return teamService.create(team);
	}
	
	@GetMapping
	public List<Team> getAll(){
		return teamService.getAll();
	}
	
	
	@PutMapping("/{id}")
	public Team editById(@PathVariable Long id,@RequestBody Team team) {
		return teamService.editById(id, team);
	}
	
	
	@GetMapping("/{id}")
	public Team getById(@PathVariable Long id) {
		return teamService.getById(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete (@PathVariable Long id) {
		 teamService.delete(id);
	}
	
	
	
}
