package com.tim10.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.dto.BranchOfficeLocationDTO;
import com.tim10.service.BranchOfficeService;

@RestController
public class BranchOfficeController {

	@Autowired
	BranchOfficeService branchOfficeService;
	

	@RequestMapping(
			value = "api/branchOffices/{rentACarId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<BranchOfficeLocationDTO>> getBranchOfficesFromRentACar( 
			@PathVariable("rentACarId") Long rentACarId) {
		
		Collection<BranchOfficeLocationDTO> brancOffices= branchOfficeService.getBranchOfficesFromRentACar(rentACarId);
		return new ResponseEntity<Collection<BranchOfficeLocationDTO>>(brancOffices, HttpStatus.OK);
		
	}
	
	
	
}
