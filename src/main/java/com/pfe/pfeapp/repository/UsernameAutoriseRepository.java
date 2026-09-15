package com.pfe.pfeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfe.pfeapp.entity.UsernameAutorise;

public interface UsernameAutoriseRepository extends JpaRepository< UsernameAutorise,Long >{
	boolean existsByUsernameAutorise(String username);
}
