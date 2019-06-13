package com.tim10.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.tim10.domain.BranchOffice;
import com.tim10.domain.Location;
import com.tim10.domain.RentACar;
import com.tim10.dto.BranchOfficeLocationDTO;
import com.tim10.repository.BranchOfficeRepository;
import com.tim10.repository.RentACarRepository;


@Service
public class BranchOfficeService {
	
	@Autowired
	BranchOfficeRepository branchOfficeRepository;
	
	@Autowired
	RentACarRepository rentACarRepository;
	
	
	
	public Collection<BranchOfficeLocationDTO> getBranchOfficesFromRentACar(Long rentACarId){
		
		return branchOfficeRepository.getBranchOfficesFromRentACar(rentACarId);
	}
	
	
	
	
	
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
			throw new ResourceNotFoundException("Rent-A-Car with id: "+rentACarId+" doesn't exist!"); 
		
		/*
		if(branchOfficeMyb)
			throw new EntityExistsException("Branch office on location: "+ lokacija +" already exists!");
		*/
		RentACar rentACar = rentACarMyb.get();
		
		
		rentACar.getBranchOffices().add(branchOffice);
		branchOffice.setMainOffice(rentACar);
		
		rentACarRepository.save(rentACar);
		
		
		return branchOffice;
	}
	

	
	public BranchOffice updateBranchOffice(BranchOffice branchOffice) throws ResourceNotFoundException {
		
		Long branchOfficeId = branchOffice.getId();
		
		Optional<BranchOffice> branchOfficeMyb = branchOfficeRepository.findById(branchOfficeId);
		//boolean branchOfficeMyb = branchOfficeRepository.existsById(branchOffice.getId()); provera na osnovu lokacije
		
		if(!branchOfficeMyb.isPresent())
			throw new ResourceNotFoundException("Branch office with id: "+branchOfficeId+" doesn't exist!"); 
		
		/*
		if(branchOfficeMyb)
			throw new EntityExistsException("Branch office on location: "+ lokacija +" already exists!");
		*/
		BranchOffice branchOfficeChange = branchOfficeMyb.get();
		
		Location locChange = branchOfficeChange.getLocation();
		Location locFrom = branchOffice.getLocation();
		
		String city = locFrom.getCity();
		String country = locFrom.getCountry();
		String image = locFrom.getImage();
		Double latitude = locFrom.getLatitude();
		Double longitude = locFrom.getLongitude();
		String street = locFrom.getStreet();
		
		
		
		if(city != null)
			locChange.setCity(city);
		
		if(country != null)
			locChange.setCountry(country);
		
		if(image != null)
			locChange.setImage(image);
		
		if(latitude != null)
			locChange.setLatitude(latitude);
		
		if(longitude != null)
			locChange.setLongitude(longitude);
		
		if(street != null)
			locChange.setStreet(street);
		
		branchOfficeChange.setLocation(locChange);
		

		branchOfficeRepository.save(branchOfficeChange);
		
		
		return branchOfficeChange;
	}
	
	
	
	public void deleteById(Long id) throws ResourceNotFoundException, Exception {
			
		Optional<BranchOffice> branchOfficeMyb = branchOfficeRepository.findById(id);
		
		if(!branchOfficeMyb.isPresent()) 
			throw new ResourceNotFoundException("Branch office with id: "+id+" doesn't exist!");
		
		if(branchOfficeMyb.get().getVehicle().size() != 0)
			throw new Exception("Branch office contains cars! (something went wrong)");
		//proveri da li ima rezervacija u ovoj filijali
		
		
		branchOfficeRepository.deleteById(id);
		
	}


	
}
