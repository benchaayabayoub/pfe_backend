package com.pfe.pfeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pfe.pfeapp.entity.Ip;
import com.pfe.pfeapp.entity.Role;
import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.repository.IpRepository;
import com.pfe.pfeapp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class IpService {

	@Autowired
	private IpRepository ipRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public Ip create(Ip ip) {
		return ipRepo.save(ip);
	}
	//get All ips:
	public List<Ip> getAll(){
		
		User user=getCurrentUser();
		
		if(user.getRole()==Role.ADMIN) {
			return ipRepo.findAll();
		}
		else{
			return ipRepo.findIpsByUserId(user.getId());
		}
	}
	
	
	public List<Ip>getIpsByPack(Long packId){
		return ipRepo.findByPackId(packId);
	}
	
	
	
	
	public Ip getById(Long id) {
		return ipRepo.findById(id).orElseThrow(()->new RuntimeException("Ip introuvable!"));
	}
	
	
	public Ip EditById(Long id,Ip nouvelle) {
		Ip old=getById(id);
		old.setAdresse(nouvelle.getAdresse());
		old.setServer(nouvelle.getServer());
		return ipRepo.save(old);
	}
	
	
	
	

	public List<Ip>getIpsGreaterThanSeuil(long seuil){
		User user=getCurrentUser();
		
		if(user.getRole()==Role.ADMIN) {
			return ipRepo.findByCumulSentGreaterThanEqual(seuil);
		}
		
		return ipRepo.findByCumulSentGreaterThanEqual(seuil).stream().filter(ip->
		ip.getPack().getUser().getId().equals(user.getId())).toList();
		
		
	}
	
	
	
	
	
	
	public void delete (Long id) {
		ipRepo.deleteById(id);
	}
	
	
	
	// methode reset cumulsent
	
	@Transactional
	public void resetNbrSent(String ipsTxt) {
		
		User user=getCurrentUser();
		
		String [] adresses=ipsTxt.split("\\r?\\n");
		
		for(String adresse:adresses) {
			String cleanAdresse=adresse.trim();
			
			/*
			if(!cleanAdresse.matches("\\d+")) {
		        throw new RuntimeException("Seulement des nombres !");
		    }
		    */
			
			if(cleanAdresse.isEmpty()) continue;
			
			Ip ip=ipRepo.findByAdresse(cleanAdresse).orElseThrow(()->new RuntimeException("Ip introuvable: "+cleanAdresse));
			
			boolean ownerIp=ip.getPack().getUser().getId().equals(user.getId());
			
			if(user.getRole() != Role.ADMIN && !ownerIp ) {
				throw new RuntimeException("Ip appartient à un autre utilisateur " + cleanAdresse);
		        
			}
			
			
			ip.setCumulSent(0);
			ipRepo.save(ip);
		}
	}
	
	
	public User getCurrentUser() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    return userRepo.findByUsernameTelegram(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
	}
	
}
