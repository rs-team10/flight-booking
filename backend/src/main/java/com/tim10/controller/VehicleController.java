package com.tim10.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Vehicle;
import com.tim10.service.VehicleService;

@RestController
public class VehicleController {
	
	
	
	@Autowired
	VehicleService vehicleService;
	
	
	
	@RequestMapping(
			value = "api/vehicles/{branchId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Vehicle>> getVehiclesFromBracnh( 
			@PathVariable("branchId") Long branchId) {
		
		Collection<Vehicle> vehicles= vehicleService.getVehiclesFromBranch(branchId);
		return new ResponseEntity<Collection<Vehicle>>(vehicles, HttpStatus.OK);
		
	}
	
	

	@RequestMapping(
			value = "api/vehicles/{branchId}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addVehicle(
			@PathVariable("branchId") Long branchId, 
			@RequestBody Vehicle vehicle){
		
		try {
			vehicleService.addVehicle(branchId ,vehicle);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		

		return new ResponseEntity<>("Vehicle is successfully saved!", HttpStatus.OK);
		
	}
	
	
	
	@RequestMapping(
			value = "api/vehicles",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changeVehicle(
			@RequestBody Vehicle vehicle){
		
		
		try {
			vehicleService.changeVehicle(vehicle);
		}catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>("Vehicle is successfully changed!", HttpStatus.OK);
		
	}
	
	
	@RequestMapping(
			value = "api/vehicle/{vehicleId}",
			method = RequestMethod.DELETE
			)
	public ResponseEntity<?> deleteVehicle( 
			@PathVariable("vehicleId") Long vehicleId) {
		
		try {
			vehicleService.deleteById(vehicleId);
		}catch(ResourceNotFoundException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Vehicle is successfully deleted!", HttpStatus.OK);
		
	}
	
	
	
	
	@RequestMapping(
			value = "api/testV/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> test(@PathVariable("id") Long id){
		
		
		return new ResponseEntity<Vehicle>(vehicleService.findById(id), HttpStatus.OK);
		
		
	}

	
	

}
