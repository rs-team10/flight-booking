package com.tim10.service;

import org.springframework.stereotype.Service;

import com.tim10.domain.RentACarService;
import com.tim10.repository.RentACarServiceRepository;

@Service
public class RentACarServiceService {
	
	

	private RentACarServiceRepository rcsRepository;
	
	public RentACarService update(RentACarService rentACarService) {
		return rcsRepository.update(rentACarService);
	}
	
	
}
