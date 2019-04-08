package com.tim10.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.Airline;
import com.tim10.repository.AirlineRepository;

@Service
public class AirlineServiceImpl{
	
	@Autowired
	private AirlineRepository airlineRepository;
	
	//==================================================
	//Dodala za dodavanje nove aviokompanije (sys admin)
	
	public List<Airline> findAll(){
		return airlineRepository.findAll();
	}
	
	public Airline save(Airline airline) {
		return airlineRepository.save(airline);
	}
	
	public Airline findOneByName(String name) {
		return airlineRepository.findOneByName(name);
	}
	
	//===================================================
	
	
	
//	@Override
//	public Collection<Airline> findAll() {
//		Collection<Airline> airlines = airlineRepository.findAll();
//		return airlines;
//	}

//	@Override
//	public Airline create(Airline airline) throws Exception {
//		if(airline.getId() != null)
//			throw new Exception("Id has to be null when creating new entity");
//		Airline savedAirline = airlineRepository.create(airline);
//		return savedAirline;
//	}
//
//	@Override
//	public Airline findByName(String name) {
//		Airline airline = airlineRepository.findByName(name);
//		return airline;
//	}
//
//	@Override
//	public Airline findById(Long id) {
//		Airline airline = airlineRepository.findById(id);
//		return airline;
//	}
//
//	@Override
//	public Airline update(Airline airline) throws Exception {
//		Airline airlineToUpdate = findById(airline.getId());
//		if(airlineToUpdate == null)
//			throw new Exception("Entity not found");
//		
//		airlineToUpdate.setName(airline.getName());
//		airlineToUpdate.setDescription(airline.getDescription());
//		airlineToUpdate.setLocation(airline.getLocation());
//		
//		Airline updatedAirline = airlineRepository.create(airlineToUpdate);
//		return updatedAirline;
//		
//	}
}
