package com.pfe.pfeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pfe.pfeapp.dto.PackFlatDto;
import com.pfe.pfeapp.entity.Pack;


public interface PackRepository extends JpaRepository<Pack, Long>{
	List<Pack> findByUserId(Long id);   // avoir la liste des packs par user

	List<Pack> findByTeamId(Long teamId);
}
