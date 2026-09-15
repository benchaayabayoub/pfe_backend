package com.pfe.pfeapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.pfeapp.service.StatsService;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

	@Autowired
	private StatsService statsService;
	
	@GetMapping("/countips")
	public long countIpsTotal() {
		return statsService.countIpsTotal();
	}
	
	@GetMapping("/countservers")
	public long countServersTotalWithoutDuplicate() {
		return statsService.countServersTotalWithoutDuplicate();
	}
	
	@GetMapping("/countpacks")
	public long countPacks() {
		return statsService.countPacksTotal();
	}
	
}
