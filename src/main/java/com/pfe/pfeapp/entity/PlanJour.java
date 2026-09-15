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
@Table(name = "plan_jours")
@Getter @Setter
public class PlanJour {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(nullable = false)
	private int jourNum;
	
	
	@Column (nullable = false)
	private long nbrSent;
	
	
	@ManyToOne
	@JoinColumn(name="plan_id", nullable = false)
	private Plan plan;

}
