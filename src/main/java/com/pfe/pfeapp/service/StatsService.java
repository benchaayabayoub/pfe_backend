package com.pfe.pfeapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfe.pfeapp.entity.Role;
import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.repository.IpRepository;
import com.pfe.pfeapp.repository.PackRepository;

@Service
public class StatsService {

	@Autowired
	private IpRepository ipRepo;
	
	@Autowired
	private PackRepository packRepo;
	
	@Autowired
	private PackService packServ;
	
	public long countIpsTotal() {
		
		User user=packServ.getCurrentUser();
		
		if(user.getRole()==Role.ADMIN) {
			return ipRepo.count();
		}
		else {
			return ipRepo.countIpsByUserId(user.getId());
		}
	}
	
	public long countServersTotalWithoutDuplicate() {
		
		User user=packServ.getCurrentUser();
		
		if(user.getRole()==Role.ADMIN) {
			return ipRepo.countServersWithoutDuplicate();
		
		}
		else {
			return ipRepo.countServersByUserId(user.getId());
		}
	}
	
	
	public long countPacksTotal() {
		
		User user=packServ.getCurrentUser();
		
		if(user.getRole()==Role.ADMIN) {
			return packRepo.count();
		}
		else {
			return packRepo.findByUserId(user.getId()).size();
		}
	}
	
}
