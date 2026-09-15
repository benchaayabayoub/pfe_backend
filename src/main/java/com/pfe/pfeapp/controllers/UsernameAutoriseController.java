package com.pfe.pfeapp.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.pfeapp.dto.UsernameDto;
import com.pfe.pfeapp.entity.UsernameAutorise;
import com.pfe.pfeapp.service.UsernameAutoriseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/userautorise")
public class UsernameAutoriseController {
	
	@Autowired
	UsernameAutoriseService usernameAutoriseService;
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody UsernameDto usernameDto) {
		try {
	        return ResponseEntity.ok(usernameAutoriseService.create(usernameDto));
	    } 
		catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	
	@GetMapping
	public ResponseEntity<?> getAllUsernames(){
		 try {
		        return ResponseEntity.ok(usernameAutoriseService.getAllUsernames());
		    } 
		 catch (RuntimeException e) {
		        return ResponseEntity.badRequest().body(e.getMessage());
		    }
	}
	
	@DeleteMapping("/{id}")
	public void deleteUserAutorise(@PathVariable Long id) {
		usernameAutoriseService.deleteUserAutorise(id);
	}
}
