package com.pfe.pfeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.pfeapp.entity.Entite;

public interface EntiteRepository extends JpaRepository<Entite, Long>{
	List<Entite> findByUserId(Long userId);
}
