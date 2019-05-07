package com.tim10.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.tim10.domain.BranchOffice;
import com.tim10.domain.Vehicle;
import com.tim10.dto.VehicleDTO;
import com.tim10.repository.BranchOfficeRepository;
import com.tim10.repository.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	BranchOfficeRepository branchOfficeRepository;
	
/*
	public void addAndReference(Long branchOfficeId, Long vehicleId)  throws Exception{
		
		Optional<BranchOffice> boM = branchOfficeRepository.findById(branchOfficeId);
		Optional<Vehicle> vM = vehicleRepository.findById(vehicleId);
		
		if(!vM.isPresent())
			throw new ResourceNotFoundException("Vehicle with id: "+vehicleId+" doesn't exist!");
		
		if(!boM.isPresent())
			throw new ResourceNotFoundException("Branch office with id: "+branchOfficeId+" doesn't exist!"); 
		
		BranchOffice branchOffice = boM.get();
		Vehicle vehicle = vM.get();
		
		branchOffice.getVehicle().add(vehicle);
		vehicle.setBranchOffice(branchOffice);

	}
	
	public Collection<VehicleDTO> getVehiclesFromBranch(Long branchId){

		return vehicleRepository.getVehiclesFromBranch(branchId); 
	}
*/	
	
	public Collection<Vehicle> getVehiclesFromBranch(Long branchId){
		
		Collection<Vehicle> vehicles = branchOfficeRepository.findById(branchId).get().getVehicle();

		return vehicles; 
	}
	
	
	public VehicleDTO getVehicleFromBranch(Long branchId, Long vehicleId) throws ResourceNotFoundException{
		
		VehicleDTO vehicle = vehicleRepository.getVehicleFromBranch(branchId, vehicleId);
		if(vehicle == null)
			throw new ResourceNotFoundException("Branch id: "+branchId+" doesn't contain vehicle id: "+ vehicleId);
		
		return  vehicle;
	}
	
	
	public List<Vehicle> findAll(){
		return vehicleRepository.findAll();
	}
	
	/*
	public Vehicle save(Vehicle vehicle) throws Exception {
		return vehicleRepository.save(vehicle);
	}
	*/
	public Vehicle update(Vehicle vehicle) {
		Long id = vehicle.getId();
		Optional<Vehicle> vehicleMyb = vehicleRepository.findById(id);
		
		if(!vehicleMyb.isPresent())
			throw new ResourceNotFoundException("Vehicle with id: "+id+" doesn't exist!");
		
		Vehicle forChange = vehicleMyb.get();
		
		forChange.setAirCondition(vehicle.getAirCondition());
		forChange.setManufacturer(vehicle.getManufacturer());
		forChange.setModel(vehicle.getModel());
		forChange.setYear(vehicle.getYear());
		forChange.setFuel(vehicle.getFuel()) ;
		forChange.setEngine(vehicle.getEngine());
		forChange.setTransmission(vehicle.getTransmission());
		forChange.setSeatsCount(vehicle.getSeatsCount());
		forChange.setAirCondition(vehicle.getAirCondition());
		forChange.setDailyRentalPrice(vehicle.getDailyRentalPrice());
		
		vehicleRepository.save(forChange);
		
		
		return forChange;
		
		
	}
	
	
	public Vehicle findById(Long id) throws ResourceNotFoundException {
		
		Optional<Vehicle> vehicleMyb = vehicleRepository.findById(id);
		
		if(!vehicleMyb.isPresent())
			throw new ResourceNotFoundException("Vehicle with id: "+id+" doesn't exist!"); 
		
		Vehicle vehicle = vehicleMyb.get();
		return vehicle;
	}
	
	
	public Vehicle addVehicle(Long branchOfficeId, Vehicle vehicle) throws Exception {
		
		Optional<BranchOffice> branchOfficeMyb = branchOfficeRepository.findById(branchOfficeId);
		
		
		if(!branchOfficeMyb.isPresent())
			throw new ResourceNotFoundException("Branch office with id: "+branchOfficeId+" doesn't exist!"); 
		
		
		BranchOffice branchOffice = branchOfficeMyb.get();
		
		
		branchOffice.getVehicle().add(vehicle);
		vehicle.setBranchOffice(branchOffice);
		
		
		branchOfficeRepository.save(branchOffice);
		//vehicleRepository.save(vehicle);
		
		
		return vehicle;
	}
	
	
	public BranchOffice test(Long branchOfficeId) {
		
		return branchOfficeRepository.findById(branchOfficeId).get();
	}
	
	
	
	
	
	public Vehicle changeVehicle(Vehicle vehicle) throws ResourceNotFoundException {
		
		Long id = vehicle.getId();
		Vehicle forChange = vehicleRepository.findById(id).get();
		
		if(forChange==null)
			throw new ResourceNotFoundException("Vehicle with id: "+ id +" doesn't exist!"); 
		
		
		//proveri kada smes da radis update
		
		
		forChange.setAirCondition(vehicle.getAirCondition());
		forChange.setManufacturer(vehicle.getManufacturer());
		forChange.setModel(vehicle.getModel());
		forChange.setYear(vehicle.getYear());
		forChange.setFuel(vehicle.getFuel()) ;
		forChange.setEngine(vehicle.getEngine());
		forChange.setTransmission(vehicle.getTransmission());
		forChange.setSeatsCount(vehicle.getSeatsCount());
		forChange.setAirCondition(vehicle.getAirCondition());
		forChange.setDailyRentalPrice(vehicle.getDailyRentalPrice());

		//setImage(String image) 
		
		
		vehicleRepository.save(forChange);
		
		return forChange;
	}
	
	
	
	public void deleteById(Long VehicleId) throws ResourceNotFoundException {

		
		Optional<Vehicle> vehicleMyb = vehicleRepository.findById(VehicleId);

		
		if(!vehicleMyb.isPresent()) 
			throw new ResourceNotFoundException("Vehicle with id: "+VehicleId+" doesn't exist!");
		//Vehicle vehicle = vehicleMyb.get();
		

		vehicleRepository.deleteById(VehicleId);
		
		

		
	}

	
}
