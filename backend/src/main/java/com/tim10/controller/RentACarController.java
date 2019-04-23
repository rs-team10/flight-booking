package com.tim10.controller;

import java.util.Collection;
import java.util.Optional;

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

import com.tim10.domain.RentACar;
import com.tim10.dto.RentACarDTO;
import com.tim10.domain.RentACarAdmin;
import com.tim10.service.RentACarService;
import com.tim10.service.UserService;

@RestController
public class RentACarController {
	
	@Autowired
	private RentACarService rentACarService;
	
	//UZMI RENT A CAR SERVICE od LOGOVANOG RENT A CAR ADMINA
	
	
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(
			value = "api/rentACars",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RentACar>> getRentACars() {
		Collection<RentACar> rentACars= rentACarService.findAll();
		return new ResponseEntity<Collection<RentACar>>(rentACars, HttpStatus.OK);
	}
		
		
	@RequestMapping(
			value = "/api/rentACars/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> getRentACar(
			@PathVariable("id") Long id) {
		
		Optional<RentACar> rentACarPres= rentACarService.findById(id);
		
		if (!rentACarPres.isPresent()) {
			return new ResponseEntity<RentACar>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<RentACar>(rentACarPres.get(), HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value = "/api/rentACar/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRentACarById(
			@PathVariable("id") Long id) {
		
		RentACarDTO rac;
		
		try {
			rac = rentACarService.getRentACarById(id);
		}catch(ResourceNotFoundException e){
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<RentACarDTO>(rac, HttpStatus.OK);
		
	}
	
	
	//===============================================
	//Dodato za registraciju RentACar servisa (sys admin)
	@RequestMapping(
			value = "/api/rentACars",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveRentACar(
			@RequestBody RentACar rentACar) throws Exception {
		if(!rentACarService.findOneByName(rentACar.getName()).isPresent()) {
			for(RentACarAdmin admin : rentACar.getAdministrators()) {
				if(userService.findOneByUsername(admin.getUsername()).isPresent()) 
					return new ResponseEntity<>("User with username: " + admin.getUsername() + " already exists!", HttpStatus.FORBIDDEN);
				else if(userService.findOneByEmail(admin.getEmail()).isPresent())
					return new ResponseEntity<>("User with email: " + admin.getEmail() + " already exists!", HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<RentACar>(rentACarService.registerRentACar(rentACar), HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Rent-a-car service with that name already exists!", HttpStatus.CONFLICT);
	}
	
	

	
	
	@RequestMapping(
			value = "/api/rentACars",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> updateRentACar(
			@RequestBody RentACar rentACar){
		
		Optional<RentACar> rentACarEx = rentACarService.findById(rentACar.getId());
		
		if(!rentACarEx.isPresent()) {
			return new ResponseEntity<RentACar>(rentACar, HttpStatus.NOT_FOUND);
		}
		
		RentACar rentACarReal = rentACarEx.get();

		//rentACarReal.setLocation(rentACar.getLocation());
		//ako se menjaju jos neki parametri...
		
		rentACarReal = rentACarService.save(rentACarReal);
		
		//GRESKA Rent-A-Car with id: 1 doesn't exist! - a ja uneo ga u bazu regularno (PITAJ ZA BAZU VISE)
		return new ResponseEntity<RentACar>(rentACarReal, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/rentACars/{id}",
			method = RequestMethod.DELETE
			)
	public ResponseEntity<Void> deleteRentACar(
			@PathVariable("id") Long id) throws Exception {
		
		try{
				rentACarService.deleteById(id);
				return ResponseEntity.noContent().build();
			  
			 }catch(ResourceNotFoundException e) {
				 
				return ResponseEntity.notFound().build(); //nasao sam negde da ovo treba da se vrati... xD
			 }
	}

}
