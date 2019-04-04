package com.tim10.service;

import org.springframework.stereotype.Service;

import com.tim10.domain.RentACar;
import com.tim10.repository.RentACarServiceRepository;

@Service
public class RentACarServiceService {
	
	

	private RentACarServiceRepository rcsRepository;
	
	public RentACar update(RentACar rentACarService) {
		return rcsRepository.update(rentACarService);
	}
	
	
}
