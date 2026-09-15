package com.pfe.pfeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.pfeapp.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long>{
	List<Plan> findByUserId(Long id);
}
