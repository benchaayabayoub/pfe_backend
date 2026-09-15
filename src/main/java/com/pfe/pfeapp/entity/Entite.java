package com.pfe.pfeapp.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="entites" , uniqueConstraints = {@UniqueConstraint(columnNames = {"nom","user_id"})}) // contraine composée : deux users peuvent creer une entite avec le meme nom
public class Entite {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (nullable = false)
	private String nom;
	
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
	
	
	/*
	@OneToMany(mappedBy = "entite", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Team> teams;
	*/
}
