package com.pfe.pfeapp.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfe.pfeapp.entity.HistoriqueEnvoi;
import com.pfe.pfeapp.entity.Ip;
import com.pfe.pfeapp.entity.Role;
import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.repository.HistoriqueEnvoiRepository;
import com.pfe.pfeapp.repository.IpRepository;

@Service
public class HistoriqueEnvoiService {

	@Autowired
	private IpRepository ipRepo;
	
	@Autowired
	private HistoriqueEnvoiRepository historiqueEnvoiRepo;
	
	@Autowired
	private IpService ipService;
	
	public void loadHistorique(String texte) {
		
		User user=ipService.getCurrentUser();
		
		String[] lignes=texte.split("\\r?\\n");
		
		for(String ligne:lignes) {
			ligne=ligne.trim();
			if(ligne.isEmpty()) continue;
			
			String [] parts=ligne.split(":");
			
			if (parts.length != 2) {
			    throw new RuntimeException("Ligne mal formée (attendu ip:valeurs) : " + ligne);
			}
			
			String adresseIp=parts[0].trim();
			String [] valeurs=parts[1].split(";");
			
			Ip ip=ipRepo.findByAdresse(adresseIp).orElseThrow(()->
			new RuntimeException("Ip: "+adresseIp+" introuvable "));
			
			
			boolean ownerIp=ip.getPack().getUser().getId().equals(user.getId());
			
			if(user.getRole() != Role.ADMIN && !ownerIp) {
				throw new RuntimeException("Ip appartient à un autre utilisateur: "+adresseIp);
			}
			
			
			long totalJour=0;
			
			for(String v:valeurs) {
				v=v.trim();
				if(v.isEmpty()) continue;
				
				long nbrSent=Long.parseLong(v);
				
				HistoriqueEnvoi he=new HistoriqueEnvoi();
				he.setIp(ip);
				he.setDate(LocalDate.now());
				he.setNbrSent(nbrSent);
				historiqueEnvoiRepo.save(he);
				
				totalJour=totalJour+nbrSent;
			}
			
			
			ip.setCumulSent(ip.getCumulSent()+totalJour);
			ipRepo.save(ip);
		}
	}
}
