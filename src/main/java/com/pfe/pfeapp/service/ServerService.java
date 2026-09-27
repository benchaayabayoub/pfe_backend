package com.pfe.pfeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pfe.pfeapp.entity.Role;
import com.pfe.pfeapp.entity.Server;
import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.repository.ServerRepository;
import com.pfe.pfeapp.repository.UserRepository;

@Service
public class ServerService {

	@Autowired
	private ServerRepository serverRepo;
	@Autowired
	private UserRepository userRepo;
	
	public Server create(Server server) {
		return serverRepo.save(server);
	}
	
	public List<Server> getAll(){
		
		User user=getCurrentUser();
		
		if(user.getRole()==Role.ADMIN) {
			return serverRepo.findAll();
		}
		else {
			return serverRepo.findServersByUserId(user.getId());
		}
	}
	
	
	public List<Server>getServersByPack(Long packId){
		return serverRepo.findServersByPackId(packId);
	}
	
	
	
	public Server getById(Long id) {
		return serverRepo.findById(id).orElseThrow(()->new RuntimeException("Server introuvable!"));
	}
	
	public Server EditById(Long id,Server old) {
		Server nouveau=getById(id);
		old.setNom(nouveau.getNom());
		return serverRepo.save(old);
		
	}
	
	public void delete(Long id) {
		serverRepo.deleteById(id);
	}
	
	public User getCurrentUser() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    return userRepo.findByUsernameTelegram(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
	}
	
}
