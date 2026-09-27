package com.pfe.pfeapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pfe.pfeapp.entity.Server;

public interface ServerRepository extends JpaRepository<Server, Long>{

	public abstract Optional<Server> findByNom(String nom);
	
	@Query("SELECT DISTINCT i.server from Ip i WHERE i.pack.user.id= :userId ")
	List<Server>findServersByUserId(@Param("userId") Long userId);
	
	@Query("SELECT DISTINCT i.server from Ip i WHERE i.pack.id= :packId")
	List<Server>findServersByPackId(@Param("packId") Long packId);
}
