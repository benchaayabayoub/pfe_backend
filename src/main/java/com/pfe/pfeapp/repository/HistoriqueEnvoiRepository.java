package com.pfe.pfeapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.pfeapp.entity.HistoriqueEnvoi;

public interface HistoriqueEnvoiRepository extends JpaRepository<HistoriqueEnvoi, Long>{
	//Optional<HistoriqueEnvoi> findTopByIpIdOrderByDateDesc(Long ipId);
	//List<HistoriqueEnvoi> findByIpIdOrderByDateAsc(Long ipId);
	List<HistoriqueEnvoi> findByIpIdOrderByDateAsc(Long ipId);
	
	List<HistoriqueEnvoi> findByIpId(Long ipId);  // pour quand on supprime Team , historique des ips aussi on doit la supprimer
	
	
}
