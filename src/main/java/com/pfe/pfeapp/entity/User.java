package com.pfe.pfeapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nom_complet",nullable = false)
	private String nomComplet;
	
	
	@Column(name="username_telegram", nullable = false, unique=true)
	private String usernameTelegram;
	
	
	@Column (nullable = false)
	private String password;
	
	
	@Enumerated(EnumType.STRING)
	private Role role;
}
