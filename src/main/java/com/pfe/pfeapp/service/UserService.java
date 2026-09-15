package com.pfe.pfeapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	
	public User getCurrentUser() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    return userRepo.findByUsernameTelegram(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
	}
	
}
