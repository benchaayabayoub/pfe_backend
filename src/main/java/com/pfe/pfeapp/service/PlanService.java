package com.pfe.pfeapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pfe.pfeapp.dto.PlanDto;
import com.pfe.pfeapp.dto.PlanJourDto;
import com.pfe.pfeapp.dto.PlanResponseDto;
import com.pfe.pfeapp.entity.Plan;
import com.pfe.pfeapp.entity.PlanJour;
import com.pfe.pfeapp.entity.Role;
import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.repository.PlanJourRepository;
import com.pfe.pfeapp.repository.PlanRepository;
import com.pfe.pfeapp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PlanService {
	
	@Autowired
	private PlanRepository planRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PlanJourRepository planJourRepo;
	
	
	
	private PlanResponseDto toResponseDto(Plan plan) {
		List<PlanJour> jours = planJourRepo.findByPlanId(plan.getId());
	    List<PlanJourDto> joursDto = jours.stream()
	            .map(j -> new PlanJourDto(j.getJourNum(), j.getNbrSent()))
	            .toList();
	    return new PlanResponseDto(plan.getId(), plan.getNom(), joursDto);
	}
	
	@Transactional
	public PlanResponseDto createPlan(PlanDto planDto) {
		Plan plan=new Plan();
		plan.setNom(planDto.getNom());
		plan.setUser(getCurrentUser());
		plan=planRepo.save(plan);
		
		String [] lignes=planDto.getJoursTxt().split("\\r?\\n");
		List<PlanJour> jours=new ArrayList<PlanJour>();
		for(String ligne:lignes){
			ligne=ligne.trim();
			if(ligne.isEmpty()) continue;
			String [] parts=ligne.split(":");
			PlanJour pj=new PlanJour();
			pj.setJourNum(Integer.parseInt(parts[0].trim()));
			pj.setNbrSent(Long.parseLong(parts[1].trim()));
			pj.setPlan(plan);
			jours.add(pj);
		}
		
		planJourRepo.saveAll(jours);
		return toResponseDto(plan);
	}
	
	
	public List<PlanResponseDto> getAll(){
		User user=getCurrentUser();
		List<Plan> plans;
		
		if(user.getRole()==Role.ADMIN) {
			plans= planRepo.findAll();
		}
		else {
			plans= planRepo.findByUserId(user.getId());
		}
		 return plans.stream().map(this::toResponseDto).toList();
	}
	
	
	public String deletePlan(Long id) {
		List<PlanJour> jours=planJourRepo.findByPlanId(id);
		planJourRepo.deleteAll(jours);
		planRepo.deleteById(id);
		return "Plan supprimé correctement";
	}
	
	public User getCurrentUser() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    return userRepo.findByUsernameTelegram(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
	}
	
}
