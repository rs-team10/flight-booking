package com.tim10.controller;

import java.text.ParseException;
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
import com.tim10.dto.VehicleSearchDTO;
import com.tim10.service.VehicleService;

@RestController
public class VehicleController {
	
	
	
	@Autowired
	VehicleService vehicleService;
	
	
	@RequestMapping(
			value = "api/vehiclesRentACar/{rentACarId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Vehicle>> getVehiclesRentACar( 
			@PathVariable("rentACarId") Long rentACarId) {
		
		Collection<Vehicle> vehicles= vehicleService.vehiclesFromRentACar(rentACarId);
		return new ResponseEntity<Collection<Vehicle>>(vehicles, HttpStatus.OK);
		
	}
	
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
			value = "api/availableVehicles/{from}/{to}/{country}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> availableVehicles(
									@PathVariable("from") String from, 
									@PathVariable("to") String to,
									@PathVariable("country") String country 
	){
		
		
		try {
			return new ResponseEntity<Collection<Vehicle>>(vehicleService.vehiclesFromCountry(from, to, country), HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>("izmeni", HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	@RequestMapping(
			value = "api/vehicleSearch/{country}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?> searchParams(
			@PathVariable("country") String country, 
			@RequestBody VehicleSearchDTO vehicleSearchDTO
		){
		
		
		
		try {
			return new ResponseEntity<Collection<Vehicle>>(vehicleService.vehiclesFilter(country, vehicleSearchDTO), HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
		
	}
	
	
	@RequestMapping(
			value = "api/vehicleSearchQuick/{country}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?> searchParamsQuick(
			@PathVariable("country") String country, 
			@RequestBody VehicleSearchDTO vehicleSearchDTO
		){
		
		
		
		try {
			return new ResponseEntity<Collection<Vehicle>>(vehicleService.vehiclesQuickFilter(country, vehicleSearchDTO), HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
		
	}
	
	
	
	
	@RequestMapping(
			value = "api/quickResVehicles/{from}/{to}/{country}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> quickResVehicles(
									@PathVariable("from") String from, 
									@PathVariable("to") String to,
									@PathVariable("country") String country 
	){
		
		
		try {
			return new ResponseEntity<Collection<Vehicle>>(vehicleService.vehiclesOnQucikFromCountry(from, to, country), HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>("izmeni", HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	@RequestMapping(
			value = "api/quickResVehicleSearch/{country}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?> quickResSearchParams(
			@PathVariable("country") String country, 
			@RequestBody VehicleSearchDTO vehicleSearchDTO
		){
		
		
		
		try {
			return new ResponseEntity<Collection<Vehicle>>(vehicleService.vehiclesQuickFilter(country, vehicleSearchDTO), HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
		
	}
	
	
	@RequestMapping(
			value = "api/testV/{country}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?> test(
			@PathVariable("country") String country, 
			@RequestBody VehicleSearchDTO vehicleSearchDTO){
		
		
		/*
		System.out.println(vehicleSearchDTO.getManufacturer()); 
		System.out.println(vehicleSearchDTO.getModel()); 
		System.out.println(vehicleSearchDTO.getYear()[0]); 
		System.out.println(vehicleSearchDTO.getYear()[1]); 
		System.out.println(vehicleSearchDTO.getFuel()); 
		System.out.println(vehicleSearchDTO.getEngine()[0]); 
		System.out.println(vehicleSearchDTO.getEngine()[1]);
		System.out.println(vehicleSearchDTO.getTransmission()); 
		System.out.println(vehicleSearchDTO.getSeatsCount()); 
		System.out.println(vehicleSearchDTO.getAirCondition()); 
		System.out.println(vehicleSearchDTO.getDailyRentalPrice()[0]); 
		System.out.println(vehicleSearchDTO.getDailyRentalPrice()[1]); 
		System.out.println(vehicleSearchDTO.getDateFrom()); 
		System.out.println(vehicleSearchDTO.getDateTo());
		System.out.println(vehicleSearchDTO.getCity());
		*/
		
		
		
		try {
			return new ResponseEntity<Collection<Vehicle>>(vehicleService.vehiclesFilter(country, vehicleSearchDTO), HttpStatus.OK);
		} catch (ParseException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
		
	}

	
	

}
