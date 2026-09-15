package com.pfe.pfeapp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pfe.pfeapp.dto.HistoriqueImportDto;
import com.pfe.pfeapp.dto.PackDto;
import com.pfe.pfeapp.dto.PackFlatDto;
import com.pfe.pfeapp.dto.PackReponseDto;
import com.pfe.pfeapp.dto.PlanDto;
import com.pfe.pfeapp.entity.HistoriqueEnvoi;
import com.pfe.pfeapp.entity.Ip;
import com.pfe.pfeapp.entity.Pack;
import com.pfe.pfeapp.entity.Plan;
import com.pfe.pfeapp.entity.Role;
import com.pfe.pfeapp.entity.Server;
import com.pfe.pfeapp.entity.Team;
import com.pfe.pfeapp.entity.User;
import com.pfe.pfeapp.repository.HistoriqueEnvoiRepository;
import com.pfe.pfeapp.repository.IpRepository;
import com.pfe.pfeapp.repository.PackRepository;
import com.pfe.pfeapp.repository.PlanRepository;
import com.pfe.pfeapp.repository.ServerRepository;
import com.pfe.pfeapp.repository.TeamRepository;
import com.pfe.pfeapp.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class PackService {

	@Autowired
	private PackRepository packRepo;
	@Autowired	
	private TeamRepository teamRepo;
	@Autowired	
	private ServerRepository serverRepo;
	@Autowired	
	private IpRepository ipRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PlanRepository planRepo;
	@Autowired
	private HistoriqueEnvoiRepository historiqueRepo;
	
	@Transactional
	public Pack createWithIps(PackDto requestCreate) {
		
		// check if team exists & find it by id:
		Team team= teamRepo.findById(requestCreate.getTeamId()).orElseThrow(()->
		new RuntimeException("Team introuvable"));
		
		Plan plan=planRepo.findById(requestCreate.getPlanId()).orElseThrow(()->
		new RuntimeException("Plan introuvable"));
		
		//create pack:
		Pack pack = new Pack();
		pack.setNom(requestCreate.getNom());
		pack.setTeam(team);
		pack.setUser(getCurrentUser());
		pack.setPlan(plan);
		pack=packRepo.save(pack);
		
		//parser les lignes : server:ip
		String[] lignes=requestCreate.getServIpList().split("\\r?\\n");
		List<Ip>ipsCrees = new ArrayList<Ip>();
		
		for (String ligne:lignes) {
			ligne=ligne.trim();
			if(ligne.isEmpty())
				continue;
			
			String[] parts=ligne.split(":");
			if(parts.length!=2) {
				throw new RuntimeException("Ligne avec format incorrect(server:ip):"+ligne);
			}
			
			String nomServer=parts[0].trim();
			String adresseIp=parts[1].trim();
			
			if(ipRepo.existsByAdresse(adresseIp)) {
				throw new RuntimeException("L\'adresse ip: "+adresseIp+" existe dans un autre pack!");
			}
			
			Server server=serverRepo.findByNom(nomServer).orElseGet(()->{
			Server nouveau=new Server();
			nouveau.setNom(nomServer);
			return serverRepo.save(nouveau);
			});
			
			
			Ip ip=new Ip();
			ip.setAdresse(adresseIp);
			ip.setServer(server);
			ip.setPack(pack);
			ipsCrees.add(ip);
			
		}
		
		
		ipRepo.saveAll(ipsCrees);
		return pack;
		
	}
	
	
	// retourne la liste des packs sans details en gérant les roles user vs admin :
	
	public List<Pack> getAll(){
		
		User user=getCurrentUser();
		if(user.getRole()==Role.ADMIN) {
			return packRepo.findAll();
		}
		else {
		
		return packRepo.findByUserId(user.getId());
		}
		
	}
	
	
	public Pack getById(Long id) {
		return packRepo.findById(id).orElseThrow(()->new RuntimeException("Pack Introuvable !"));
	}
	
	
	/* code delete pack simple avec l id
	 
	public void delete(Long id) {
		packRepo.deleteById(id);
	}
	*/
	
	
	
	
	
	
	public List<Ip> getIpsOfPack(Long packId){
		return ipRepo.findByPackId(packId);
	}
	
	
	// recupérer la liste des packs avec serv et ips et history en gerant le role admin vs user:

	public List<PackReponseDto> getAllPackInfo(){
		
		User user=getCurrentUser();
		List<Pack>packs;
		List<PackReponseDto>result=new ArrayList<>();
		
		if(user.getRole()==Role.ADMIN) {
			packs=packRepo.findAll();
		}
		else {
			packs=packRepo.findByUserId(user.getId());
		}
		
		for (Pack pack:packs) {
			
			List<Ip>ips=ipRepo.findByPackId(pack.getId());
			
			List<PackReponseDto.IpsReponseDto>ipsDto=ips.stream()
					.map(ip->{List<PackReponseDto.HistoriqueDto> hist=historiqueRepo.findByIpIdOrderByDateAsc(ip.getId())
					.stream()
					.map(h->new PackReponseDto.HistoriqueDto(h.getDate().toString(), h.getNbrSent()))
					.toList();
				return new PackReponseDto.IpsReponseDto(ip.getServer().getNom(), ip.getAdresse(), ip.getCumulSent(), hist);
				}).toList();
			result.add(new PackReponseDto(pack.getId(), pack.getNom(), pack.getTeam().getNom(),pack.getTeam().getId(), ipsDto));
		}
		
		return result;
		
	}
	
	
	// code delete pack with his ips & servers "if servers not present in other packs":
	
	public void deletePack(Long id) {
		List<Ip> packIps=ipRepo.findByPackId(id);
		Set<Server> listeServers=new HashSet<>();
		for(Ip ip : packIps) {
			Server server=ip.getServer();
			listeServers.add(server);
			
			// Nettoyage de l'historique de cette IP avant de la supprimer
			List<HistoriqueEnvoi> historiques = historiqueRepo.findByIpId(ip.getId());
		    historiqueRepo.deleteAll(historiques);
		}
		
	
		
		ipRepo.deleteAll(packIps);
		
		for(Server server : listeServers) {
			if (!ipRepo.existsByServerId(server.getId())){
				serverRepo.deleteById(server.getId());
			}
		}
		
		packRepo.deleteById(id);
	}
	
	
	
	
	
	public User getCurrentUser() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    return userRepo.findByUsernameTelegram(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
	}
	
	
	
	
	
	
}
