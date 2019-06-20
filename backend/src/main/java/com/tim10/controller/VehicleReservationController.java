package com.tim10.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.dto.QuickResSendDTO;
import com.tim10.dto.QuickVehicleReservationDTO;
import com.tim10.dto.VehicleReservationDTO;
import com.tim10.dto.VehicleReservationPrewDTO;
import com.tim10.dto.VehicleReservationPrewQuickDTO;
import com.tim10.service.VehicleReservationService;

@RestController
public class VehicleReservationController {

	

	@Autowired
	VehicleReservationService vehicleReservationService;
	
	
	
	@RequestMapping(
			value = "api/vehicleReservationPrew/{vehicleId}",//dodat datum
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getReservationPrew(@PathVariable("vehicleId") Long vehicleId) {
		
		
		VehicleReservationPrewDTO forRet;
		
		try {
			forRet=vehicleReservationService.forPreview(vehicleId);
		}
		catch(ResourceNotFoundException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
	
	
	@RequestMapping(
			value = "api/getQuickReservations/{rentACarId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getQuickReservations( 
			@PathVariable("rentACarId") Long rentACarId) {
		Collection<QuickResSendDTO>  forRet;
		
		try {
			forRet=vehicleReservationService.getQuickReservations(rentACarId);
		}
		catch(ResourceNotFoundException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Collection<QuickResSendDTO>>(forRet, HttpStatus.OK);

		
	}
	
	
	@RequestMapping(
			value = "api/getQuickResFromVehicle/{from}/{to}/{vehicleId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllQuickResFromVehicle(
			@PathVariable("vehicleId") Long vehicleId, 
			@PathVariable("from") String from, 
			@PathVariable("to") String to){
		
		VehicleReservationPrewQuickDTO  forRet;
		
		try {
			forRet=vehicleReservationService.getAllQuickResFromVehicle(vehicleId, from, to);
		}
		catch(ResourceNotFoundException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<VehicleReservationPrewQuickDTO>(forRet, HttpStatus.OK);
		
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
			
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (IOException e) {
			return new ResponseEntity<String>("Vozilo je tada zauzeto", HttpStatus.BAD_REQUEST);
		} catch (InterruptedException e) {
			return new ResponseEntity<String>("pa ti vidi", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Nesto sasvim trece", HttpStatus.BAD_REQUEST);
		}
		
		
		return new ResponseEntity<>("Successfully saved!", HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "api/quickVehicleReservation/",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createQuickVehicleReservation( 
			@RequestBody QuickVehicleReservationDTO vehicleRes) {
		
		
		try {
			
			vehicleReservationService.createQucikVehicleReservation(vehicleRes);
			
		}catch(ResourceNotFoundException e){
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (IOException e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		
		
		return new ResponseEntity<>("Successfully saved!", HttpStatus.OK);
	}
	
	
	
	@RequestMapping(
			value = "api/confirmQuickVehicle/{mainResId}/{vehicleResId}",
			method = RequestMethod.POST)
	public ResponseEntity<?> confirQuickVehicleRes( 
			@PathVariable("mainResId") Long mainResId,
			@PathVariable("vehicleResId") Long vehicleResId) {
		
		
		try {
			
			vehicleReservationService.confirQuickVehicleRes(mainResId,vehicleResId);
			
		}catch(ResourceNotFoundException e){
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(ObjectOptimisticLockingFailureException e) {
			return new ResponseEntity<>("Already reserved", HttpStatus.NOT_FOUND);
		}
		//add u main reservation
		
		//dell
		catch (Exception e) {

			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("Successfully saved!", HttpStatus.OK);
	}
	
	
	
	
}
