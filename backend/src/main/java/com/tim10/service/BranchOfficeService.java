package com.tim10.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.tim10.domain.BranchOffice;
import com.tim10.domain.RentACar;
import com.tim10.repository.BranchOfficeRepository;
import com.tim10.repository.RentACarRepository;


@Service
public class BranchOfficeService {
	
	@Autowired
	BranchOfficeRepository branchOfficeRepository;
	
	@Autowired
	RentACarRepository rentACarRepository;
	
	
	
	public Set<BranchOffice> findBranchOffices(Long rentACarId) throws ResourceNotFoundException {
		
		Optional<RentACar> rentACarMyb = rentACarRepository.findById(rentACarId);
		
		if(!rentACarMyb.isPresent())
			throw new ResourceNotFoundException("Rent-A-Car with id: "+rentACarId+" doesn't exist!"); 
		
		
		RentACar rentACar = rentACarMyb.get();
		return rentACar.getBranchOffices();
	}
		
	
	public BranchOffice findById(Long id) throws ResourceNotFoundException {
		
		Optional<BranchOffice> branchOfficeMyb = branchOfficeRepository.findById(id);
		
		if(!branchOfficeMyb.isPresent())
			throw new ResourceNotFoundException("BranchOffice with id: "+id+" doesn't exist!"); 
		
		BranchOffice bo = branchOfficeMyb.get();
		return bo;
	}
	
	
	public BranchOffice addBranchOffices(Long rentACarId, BranchOffice branchOffice) throws Exception {
		
		Optional<RentACar> rentACarMyb = rentACarRepository.findById(rentACarId);
		//boolean branchOfficeMyb = branchOfficeRepository.existsById(branchOffice.getId()); provera na osnovu lokacije
		
		if(!rentACarMyb.isPresent())
			throw new ResourceNotFoundException("Rent=A=Car with id: "+rentACarId+" doesn't exist!"); 
		
		/*
		if(branchOfficeMyb)
			throw new EntityExistsException("Branch office on location: "+ lokacija +" already exists!");
		*/
		RentACar rentACar = rentACarMyb.get();
		rentACar.getBranchOffices().add(branchOffice);
		
		
		return branchOffice;
	}
	
	//update fali - vidi kada smes da radis update...
	
	
	public void deleteById(Long id) throws Exception {
			
		Optional<BranchOffice> branchOfficeMyb = branchOfficeRepository.findById(id);
		
		if(!branchOfficeMyb.isPresent()) 
			throw new ResourceNotFoundException("Branch office with id: "+id+" doesn't exist!");
		
		//proveri da li ima rezervacija u ovoj filijali
		
		branchOfficeRepository.deleteById(id);
		
	}


	
}
