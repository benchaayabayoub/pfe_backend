package com.pfe.pfeapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {
	
	
	@GetMapping("/jj")
	public String home() {
		return "welcome to pfe app";
	}
	
	
	@GetMapping("/test-protected")
	public String testProtected() {
	    return "Tu es bien authentifié !";
	}
}






