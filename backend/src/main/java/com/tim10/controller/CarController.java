package com.tim10.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.Vehicle;
import com.tim10.service.CarService;



@CrossOrigin
@RestController
public class CarController {

	
	@Autowired
	private CarService carService;
	
	
	
	@RequestMapping(
			value = "/api/cars",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Vehicle>> getCars() {
		Collection<Vehicle> cars = carService.findAll();
		return new ResponseEntity<Collection<Vehicle>>(cars, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/cars",
			method = RequestMethod.GET,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Vehicle>> getCars(Vehicle car) {
		Collection<Vehicle> cars = carService.find(car);
		return new ResponseEntity<Collection<Vehicle>>(cars, HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(
			value = "/api/cars/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vehicle> getCar(@PathVariable("id") Long id) {
		Vehicle car = carService.findById(id);
		if (car == null) {
			return new ResponseEntity<Vehicle>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vehicle>(car, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/addCar",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vehicle> createCar(@RequestBody Vehicle car) throws Exception {
		Vehicle savedCar = carService.create(car);
		return new ResponseEntity<Vehicle>(savedCar, HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value = "/api/deleteCar",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vehicle> deleteCAr(@PathVariable("id")  Long id) throws Exception {
		Vehicle deletedCar = carService.delete(id);
		return new ResponseEntity<Vehicle>(deletedCar, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(
			value = "/api/updateCar",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vehicle> updateCar(@RequestBody Vehicle car) throws Exception {
		Vehicle updatedCar = carService.update(car);
		if (updatedCar == null)
			return new ResponseEntity<Vehicle>(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<Vehicle>(updatedCar, HttpStatus.OK);
	}
	
	
	

}
