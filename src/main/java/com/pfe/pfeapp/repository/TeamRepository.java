package com.pfe.pfeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pfe.pfeapp.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{
	
	
	List<Team> findByUserId(Long userId);
	
	
	List<Team> findByEntiteId(Long entiteId);
	
	
	
}
