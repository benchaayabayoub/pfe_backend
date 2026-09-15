package com.pfe.pfeapp.entity;

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
@Table(name = "ips")
@Getter
@Setter
public class Ip {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String adresse;
	
	@ManyToOne
	@JoinColumn (name = "server_id" , nullable = false)
	private Server server;
	
	@ManyToOne
	@JoinColumn(name="pack_id",nullable = false)
	private Pack pack;
	
	@Column(nullable = false)
	private long cumulSent;
}
