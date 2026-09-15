package com.pfe.pfeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pfe.pfeapp.entity.Entite;
import com.pfe.pfeapp.entity.Pack;
import com.pfe.pfeapp.entity.Role;
import com.pfe.pfeapp.entity.Team;
import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.repository.EntiteRepository;
import com.pfe.pfeapp.repository.PackRepository;
import com.pfe.pfeapp.repository.TeamRepository;
import com.pfe.pfeapp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class EntiteService {

	@Autowired
	private EntiteRepository entiteRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private PackService packService;
	
	@Autowired
	private PackRepository packRepo;
	
	public Entite create(Entite entite) {
		
		User user=getCurrentUser();
		entite.setUser(user);
		return entiteRepo.save(entite);
		
	}
	
	public List<Entite> getAll(){
		
		User user=getCurrentUser();
		
		if(user.getRole()==Role.ADMIN) {
			return entiteRepo.findAll();
		}
		else {
			return entiteRepo.findByUserId(user.getId());
		}
	}
	
	public Entite getById(Long id) {
		return entiteRepo.findById(id).orElseThrow(()->new RuntimeException("Entité Introuvable!"));
	}
	
	public Entite EditById(Long id , Entite nouvelle) {
		Entite old=getById(id);
		old.setNom(nouvelle.getNom());
		return entiteRepo.save(old);
	} 
	
	@Transactional
	public void deleteEntiteWithTeams(Long id) {
		
	    List<Team> teams = teamRepo.findByEntiteId(id);
	    for (Team team : teams) {
	        List<Pack> packs = packRepo.findByTeamId(team.getId()); 
	        for (Pack pack : packs) {
	            packService.deletePack(pack.getId());
	        }

	    }
	    teamRepo.deleteAll(teams);
		entiteRepo.deleteById(id);
	}
	
	public User getCurrentUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    return userRepo.findByUsernameTelegram(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
	}
}
