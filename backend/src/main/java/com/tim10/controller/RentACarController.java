package com.tim10.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import com.tim10.dto.NewRentACarDTO;
import com.tim10.dto.RcsDTO;
import com.tim10.dto.RentACarDTO;
import com.tim10.dto.RentACarReportDTO;
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
		
	
	@RequestMapping(value="api/rentACars/rentACarPage", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RcsDTO>> getRentACars(Pageable page){
		List<RcsDTO> dtos = new ArrayList<RcsDTO>();
		for(RentACar rcs : rentACarService.findAll(page))
			dtos.add(new RcsDTO(rcs));
		return new ResponseEntity<List<RcsDTO>>(dtos, HttpStatus.OK);
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
			@RequestBody NewRentACarDTO rentACarDTO) throws Exception {
		RentACar registeredRentACar = null;
		RentACar rentACar = new RentACar(rentACarDTO);
		try {
			registeredRentACar = rentACarService.registerRentACar(rentACar);
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(registeredRentACar, HttpStatus.CREATED);

//		if(!rentACarService.findOneByName(rentACar.getName()).isPresent()) {
//			for(RentACarAdmin admin : rentACar.getAdministrators()) {
//				if(userService.findOneByUsername(admin.getUsername()).isPresent()) 
//					return new ResponseEntity<>("User with username: " + admin.getUsername() + " already exists!", HttpStatus.FORBIDDEN);
//				else if(userService.findOneByEmail(admin.getEmail()).isPresent())
//					return new ResponseEntity<>("User with email: " + admin.getEmail() + " already exists!", HttpStatus.FORBIDDEN);
//			}
//			return new ResponseEntity<RentACar>(rentACarService.registerRentACar(rentACar), HttpStatus.CREATED);
//		}
//		return new ResponseEntity<>("Rent-a-car service with that name already exists!", HttpStatus.CONFLICT);
	}
	
	

	
	
	@RequestMapping(
			value = "/api/rentACars",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRentACar(
			@RequestBody RentACar rentACar){
		
		Optional<RentACar> rentACarEx = rentACarService.findById(rentACar.getId());
		
		System.out.println(rentACar.getId());
		
		
		if(!rentACarEx.isPresent()) {
			return new ResponseEntity<>("Rent-a-car not found", HttpStatus.NOT_FOUND);
		}
		
		RentACar rentACarReal = rentACarEx.get();

		//rentACarReal.setLocation(rentACar.getLocation());
		//ako se menjaju jos neki parametri...
		
		rentACarReal.setName(rentACar.getName());
		rentACarReal.setDescription(rentACar.getDescription());
		
		
		rentACarReal = rentACarService.save(rentACarReal);
		
		return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
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
	
	@RequestMapping(value="/api/rentACarReport/{rentACarId}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACarReportDTO> getReport(
			@PathVariable("rentACarId") Long rentACarId) throws ParseException{
		return new ResponseEntity<>(rentACarService.getReports(rentACarId), HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/rentACarIncomeReport", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BigDecimal> getIncomeReport(
			@PathParam("rentACarId") Long rentACarId,
			@PathParam("dateFrom") String dateFrom,
			@PathParam("dateTo") String dateTo) throws ParseException {
		
		return new ResponseEntity<>(rentACarService.getIncomeReport(rentACarId,  dateFrom,  dateTo), HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/rentACarDailyReport", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> getDailyReport(
			@PathParam("rentACarId") Long rentACarId,
			@PathParam("dateFrom") String dateFrom) throws ParseException{
		return new ResponseEntity<>(rentACarService.getDailyReport(rentACarId, dateFrom), HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/rentACarWeeklyReport", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> gteWeeklyReport(
			@PathParam("rentACarId") Long rentACarId,
			@PathParam("dateFrom") String dateFrom) throws ParseException{
		return new ResponseEntity<>(rentACarService.getWeeklyReport(rentACarId, dateFrom), HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/rentACarMonthlyReport", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Long, Integer>> getYearlyReport(
			@PathParam("rentACarId") Long rentACarId,
			@PathParam("numberOfYears") int numberOfYears) throws ParseException{
		return new ResponseEntity<>(rentACarService.getYearlyReport(rentACarId, numberOfYears), HttpStatus.OK);
	}
	

}
