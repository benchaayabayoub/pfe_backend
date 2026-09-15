package com.pfe.pfeapp.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "historique_envoi")
@Getter
@Setter
public class HistoriqueEnvoi {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ip_id",nullable = false)
	private Ip ip;
	
	@Column(nullable = false)
	private LocalDate date;
	
	@Column(nullable = false)
	private long nbrSent;
	
}
