package com.pfe.pfeapp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pfe.pfeapp.dto.PlanDto;
import com.pfe.pfeapp.dto.PlanJourDto;
import com.pfe.pfeapp.dto.PlanResponseDto;
import com.pfe.pfeapp.entity.HistoriqueEnvoi;
import com.pfe.pfeapp.entity.Ip;
import com.pfe.pfeapp.entity.Pack;
import com.pfe.pfeapp.entity.Plan;
import com.pfe.pfeapp.entity.PlanJour;
import com.pfe.pfeapp.entity.Role;
import com.pfe.pfeapp.entity.Server;
import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.repository.HistoriqueEnvoiRepository;
import com.pfe.pfeapp.repository.IpRepository;
import com.pfe.pfeapp.repository.PackRepository;
import com.pfe.pfeapp.repository.PlanJourRepository;
import com.pfe.pfeapp.repository.PlanRepository;
import com.pfe.pfeapp.repository.ServerRepository;
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
	
	@Autowired
	private PackRepository packRepo;
	
	@Autowired
	private HistoriqueEnvoiRepository historiqueRepo;
	
	@Autowired
	private IpRepository ipRepo;
	
	
	@Autowired
	private ServerRepository serverRepo;
	
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
			
			if(parts.length!=2) {
				throw new RuntimeException("Format incorrect! essaye x:x");
			}
			
			try {
				PlanJour pj=new PlanJour();
				pj.setJourNum(Integer.parseInt(parts[0].trim()));
				pj.setNbrSent(Long.parseLong(parts[1].trim()));
				pj.setPlan(plan);
				jours.add(pj);
			} catch (Exception e) {
				throw new RuntimeException("Format incorrect! essaye numéro jour:nbr sent");
			}
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
	
	
	@Transactional
	public void deletePlan(Long id) {

	    Plan plan = planRepo.findById(id)
	        .orElseThrow(() -> new RuntimeException("Plan introuvable!"));

	    List<Pack> packs = packRepo.findByPlanId(id);

	    Set<Server> serversToCheck = new HashSet<>();

	    for (Pack pack : packs) {

	        List<Ip> ips = ipRepo.findByPackId(pack.getId());
	        

	        
	        for (Ip ip : ips) {
 
	            // delete historiques
	            historiqueRepo.deleteAll(
	                historiqueRepo.findByIpId(ip.getId())
	            );

	            serversToCheck.add(ip.getServer());
	        }

	        // delete IPs
	        ipRepo.deleteByPackId(pack.getId());

	        // delete pack
	        packRepo.delete(pack);
	    }

	    // cleanup servers
	    for (Server server : serversToCheck) {
	        if (!ipRepo.existsByServerId(server.getId())) {
	            serverRepo.deleteById(server.getId());
	        }
	    }

	    // delete plan jours
	    planJourRepo.deleteAll(planJourRepo.findByPlanId(id));

	    // delete plan
	    planRepo.delete(plan);

	    
	}
	
	
	
	
	
	public User getCurrentUser() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    return userRepo.findByUsernameTelegram(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
	}
	
}
