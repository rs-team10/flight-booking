package com.tim10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.RentACar;
import com.tim10.service.RentACarServiceService;

@CrossOrigin
@RestController
public class RentACarServiceController {

	@Autowired
	private RentACarServiceService rcsService;
	
	//===============================================
	//Dodato za registraciju RentACar servisa (sys admin)
	@RequestMapping(
			value="api/rentacar/registerRentACar",
			method=RequestMethod.POST,
			consumes="application/json")
	public ResponseEntity<?> registerRentACar(@RequestBody RentACar rentacar){
		
		if(rcsService.findOneByName(rentacar.getName()) == null) {
			RentACar returnRentACar = rcsService.save(rentacar);
			return new ResponseEntity<>(returnRentACar, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>("Rent-a-car service with that name already exists!", HttpStatus.FORBIDDEN);
	}
	//===============================================
	
	
	
//	@RequestMapping(
//			value = "/api/updateRcs",
//			method = RequestMethod.PUT,
//			consumes = MediaType.APPLICATION_JSON_VALUE,
//			produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<RentACar> updateCar(@RequestBody RentACar rentACarService) throws Exception {
//		RentACar updatedRcs = rcsService.update(rentACarService);
//		if (updatedRcs == null)
//			return new ResponseEntity<RentACar>(HttpStatus.INTERNAL_SERVER_ERROR);
//		return new ResponseEntity<RentACar>(updatedRcs, HttpStatus.OK);
//	}
	


	
	
	
}
