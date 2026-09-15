package com.pfe.pfeapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pfe.pfeapp.entity.Ip;

public interface IpRepository extends JpaRepository<Ip, Long>{

	public abstract boolean existsByAdresse(String adresse); //verifier si une ip existe deja (pour bloquer les doublons)
	public abstract List<Ip> findByPackId(Long packId);  //liste les ips par pack  (par son id)
	public abstract boolean existsByServerId(Long serverId); // verifie si un serv a encore des ips , pour la supp en cascade
	
	Optional<Ip>findByAdresse(String adresse);   //trouver l adresse ip recherchée
	
	
	List<Ip>findByCumulSentGreaterThanEqual(long seuil);  //retourner ips egales ou supérieurs à un nbr
	
	@Query("SELECT COUNT(DISTINCT i.server.id) FROM Ip i")
	public abstract long countServersWithoutDuplicate();
	
	@Query("SELECT COUNT (i) FROM Ip i where i.pack.user.id= :userId")
	public abstract long countIpsByUserId(@Param ("userId") Long userId);
	
	@Query("SELECT COUNT (DISTINCT i.server.id) FROM Ip i where i.pack.user.id= :userId")
	public abstract long countServersByUserId(@Param ("userId") Long userId);
	
	
	// recuperer les ips par user : via (ip->pack->user):
	
	@Query("SELECT i FROM Ip i WHERE i.pack.user.id=:userId")
	List<Ip> findIpsByUserId(@Param("userId") Long userId);
	
	
	
}
