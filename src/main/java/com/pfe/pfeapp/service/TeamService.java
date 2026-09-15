package com.pfe.pfeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pfe.pfeapp.entity.Pack;
import com.pfe.pfeapp.entity.Role;
import com.pfe.pfeapp.entity.Team;
import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.repository.PackRepository;
import com.pfe.pfeapp.repository.TeamRepository;
import com.pfe.pfeapp.repository.UserRepository;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PackRepository packRepo;
	
	
	@Autowired
	private PackService packService;

	public Team create(Team team) {
		
		team.setUser(getCurrentUser());
		return teamRepo.save(team);
	}
	
	public List<Team> getAll(){
		
		User user=getCurrentUser();
		if(user.getRole()==Role.ADMIN) {
			return teamRepo.findAll();
		}
		else {
			return teamRepo.findByUserId(user.getId());
		}
	}
	
	public Team getById(Long id) {
		return teamRepo.findById(id).orElseThrow(()->new RuntimeException("Team introuvable"));
	}
	
	public Team editById(Long id , Team nouvelle) {
		Team old=getById(id);
		old.setNom(nouvelle.getNom());
		old.setEntite(nouvelle.getEntite());
		return teamRepo.save(old);
	}
	
	public void delete(Long id) {
		
		
	    List<Pack> packs = packRepo.findByTeamId(id);
	    for (Pack pack : packs) {
	        packService.deletePack(pack.getId());
	    }
	    teamRepo.deleteById(id);
	}
	
	
	
	public User getCurrentUser() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    return userRepo.findByUsernameTelegram(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
	}
}
