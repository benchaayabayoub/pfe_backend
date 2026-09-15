package com.pfe.pfeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfe.pfeapp.dto.UsernameDto;
import com.pfe.pfeapp.entity.Role;
import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.entity.UsernameAutorise;
import com.pfe.pfeapp.repository.UsernameAutoriseRepository;

@Service
public class UsernameAutoriseService {

	@Autowired
	private UsernameAutoriseRepository userNameAutoriseRepo;
	
	@Autowired
	private UserService userService;
	
	
	
	public void onlyAdmin() {
		User user= userService.getCurrentUser();
		
		if(user.getRole()!=Role.ADMIN) {
			throw new RuntimeException("Accès seulement pour l'admin");
		}
	}
	
	public UsernameAutorise create(UsernameDto requestCreate) {
		
		onlyAdmin();
		
		UsernameAutorise ua=new UsernameAutorise();
		ua.setUsernameAutorise(requestCreate.getUsernameAutorise());
		return userNameAutoriseRepo.save(ua);
		
	}
	
	
	public List<UsernameAutorise>getAllUsernames(){
		
		onlyAdmin();
		return userNameAutoriseRepo.findAll();
	}
	
	public void deleteUserAutorise(Long id) {
		
		onlyAdmin();
		userNameAutoriseRepo.deleteById(id);
	}
}
