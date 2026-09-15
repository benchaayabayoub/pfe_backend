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

import com.pfe.pfeapp.entity.Server;
import com.pfe.pfeapp.service.ServerService;

@RestController
@RequestMapping("/api/servers")
public class ServerController {

	@Autowired
	private ServerService serverService;
	
	
	@PostMapping
	public Server create(@RequestBody Server server) {
		return serverService.create(server);
	}
	
	
	@GetMapping
	public List<Server> getAll(){
		return serverService.getAll();
	}
	
	
	@GetMapping("/{id}")
	public Server getById(@PathVariable Long id) {
		return serverService.getById(id);
	}
	
	
	@PutMapping("/{id}")
	public Server editById(@PathVariable Long id, @RequestBody Server server) {
		return serverService.EditById(id, server);
				
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		serverService.delete(id);
	}
	
	
	
	
	
	
	
}
