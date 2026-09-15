package com.pfe.pfeapp.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pfe.pfeapp.dto.Login;
import com.pfe.pfeapp.dto.Register;
import com.pfe.pfeapp.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Register request) {
        try {
        	authService.register(request);
            return ResponseEntity.ok("Compte créé avec succès");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody Login request) {
    	try {
            String token = authService.login(request);
            return ResponseEntity.ok(Map.of("token",token,"message","Connexion réussie"));
		} catch (ResponseStatusException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
		}

    }
}