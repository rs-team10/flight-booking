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

import com.tim10.domain.QuickVehicleReservation;
import com.tim10.dto.QuickVehicleReservationDTO;
import com.tim10.dto.VehicleReservationDTO;
import com.tim10.dto.VehicleReservationPrewDTO;
import com.tim10.service.VehicleReservationService;

@RestController
public class VehicleReservationController {

	

	@Autowired
	VehicleReservationService vehicleReservationService;
	
	
	
	@RequestMapping(
			value = "api/vehicleReservationPrew/{vehicleId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getReservationPrew( 
			@PathVariable("vehicleId") Long vehicleId) {
		
		
		VehicleReservationPrewDTO forRet;
		
		try {
			forRet=vehicleReservationService.forPreview(vehicleId);
		}
		catch(ResourceNotFoundException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<VehicleReservationPrewDTO>(forRet, HttpStatus.OK);

		
	}
	
	
	
	@RequestMapping(
			value = "api/QuickVehicleReservation/{rentACarId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> rentACarQuick( 
			@PathVariable("rentACarId") Long rentACarId) {
		
		
		Collection<QuickVehicleReservationDTO>  forRet;
		
		try {
			forRet=vehicleReservationService.rentACarQuick(rentACarId);
		}
		catch(ResourceNotFoundException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Collection<QuickVehicleReservationDTO>>(forRet, HttpStatus.OK);

		
	}
	
	
	
	/*
	@RequestMapping(
			value = "api/vehicleReservation/{mainReservationId}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveVehicleReservation( 
			@PathVariable("mainReservationId") Long mainReservationId,
			@RequestBody VehicleReservation vehicleRes) {
		
		
		
		try {
			vehicleReservationService.saveVehicleReservation(mainReservationId, vehicleRes);
		}
		catch(ResourceNotFoundException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>("Successfully saved!", HttpStatus.OK);

		
	}
	*/
	@RequestMapping(
			value = "api/vehicleReservation/{mainReservationId}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> TEMPsaveVehicleReservation( 
			@PathVariable("mainReservationId") Long mainReservationId,
			@RequestBody VehicleReservationDTO vehicleRes) {
		
		
		try {
			
			vehicleReservationService.saveVehicleReservation(mainReservationId, vehicleRes);
			
		}catch(ResourceNotFoundException e){
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<>("Successfully saved!", HttpStatus.OK);
	}
	
	
	
	
	
	
}
