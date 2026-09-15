package com.pfe.pfeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.pfeapp.entity.PlanJour;

public interface PlanJourRepository extends JpaRepository<PlanJour, Long>{

	
	//trouver les jours d un plan
	List<PlanJour> findByPlanId(Long planId);
}
