package com.tim10.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.RentACar;
import com.tim10.repository.IRentACarServiceRepository;

@Service
public class RentACarServiceService {
	
	@Autowired
	private IRentACarServiceRepository rcsRepository;
	
	//================================================
	//Dodato za dodavanje novog RentACar servisa (sys admin)
	
	public List<RentACar> findAll(){
		return rcsRepository.findAll();
	}
	
	public RentACar save(RentACar rentacar) {
		return rcsRepository.save(rentacar);
	}
	
	public RentACar findOneByName(String name) {
		return rcsRepository.findOneByName(name);
	}
	
	//================================================

	
	//	public RentACar update(RentACar rentACarService) {
//		return rcsRepository.update(rentACarService);
//	}
	
	
}
