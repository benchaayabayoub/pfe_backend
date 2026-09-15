package com.pfe.pfeapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfe.pfeapp.dto.PackDto;
import com.pfe.pfeapp.dto.PackReponseDto;
import com.pfe.pfeapp.entity.Ip;
import com.pfe.pfeapp.entity.Pack;
import com.pfe.pfeapp.service.PackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/packs")
public class PackController {

	@Autowired
	private PackService packService;
	
	
	@PostMapping
	public Pack create(@Valid @RequestBody PackDto packDto) {
		return packService.createWithIps(packDto);
	}
	
	
	// route getAll pack sans servers & ips , elle appelle la methode getAll()  depuis packService
	@GetMapping
	public List<Pack>getAll(){
		return packService.getAll();
	}
	
	
	//route getAll packs avec details servers et ips , appellée depuis packService:
	
	
	@GetMapping("/details")
	public List<PackReponseDto> getAllPackInfo(){
		return packService.getAllPackInfo();
	}
	
	@GetMapping("/{id}")
	public Pack getById(@PathVariable Long id) {
		return packService.getById(id);
	}
	
	
	/*
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		packService.delete(id);
	}
	*/
	
	
	@DeleteMapping("/{id}")
	public void deletPack(@PathVariable Long id) {
		packService.deletePack(id);
	}
	
	@GetMapping("/{id}/ips")
	public List<Ip> getIpsOfPack(@PathVariable Long id){
		return packService.getIpsOfPack(id);
	}
	
	
}
