package com.pfe.pfeapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.pfeapp.entity.Ip;
import com.pfe.pfeapp.service.IpService;

@RestController
@RequestMapping("/api/ips")
public class IpController {

	@Autowired
	private IpService ipService;
	
	@PostMapping
	public Ip create(@RequestBody Ip ip) {
		return ipService.create(ip);
	}
	
	@GetMapping
	public List<Ip> getAll(){
		return ipService.getAll();
	}
	
	@GetMapping("/{id}")
	public Ip getById(@PathVariable Long id) {
		return ipService.getById(id);
	}
	
	@PutMapping("/{id}")
	public Ip editById(@PathVariable Long id,@RequestBody Ip ip) {
		return ipService.EditById(id, ip);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		ipService.delete(id);
	}
	
	
	@GetMapping("/seuil")
	public List<Ip>getIpsGreaterThanSeuil(@RequestParam long seuil){
		return ipService.getIpsGreaterThanSeuil(seuil);
	}
	
	
}
