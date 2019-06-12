package com.tim10.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.Destination;
import com.tim10.dto.DestinationDTO;
import com.tim10.repository.DestinationRepository;

@Service
public class DestinationService {
	
	@Autowired
	DestinationRepository destinationRepository;
	
	public List<DestinationDTO> getAllDestinations() {
		
		List<DestinationDTO> destinationsList = new ArrayList<DestinationDTO>();
		
		for(Destination destination : destinationRepository.findAll()) {
			DestinationDTO destinationDTO = new DestinationDTO(destination);
			destinationsList.add(destinationDTO);
		}
		
		return destinationsList;
	}
	
}
