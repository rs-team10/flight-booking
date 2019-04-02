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

import com.tim10.domain.Car;
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
	public ResponseEntity<Collection<Car>> getCars() {
		Collection<Car> cars = carService.findAll();
		return new ResponseEntity<Collection<Car>>(cars, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/cars",
			method = RequestMethod.GET,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Car>> getCars(Car car) {
		Collection<Car> cars = carService.find(car);
		return new ResponseEntity<Collection<Car>>(cars, HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(
			value = "/api/cars/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> getCar(@PathVariable("id") Long id) {
		Car car = carService.findById(id);
		if (car == null) {
			return new ResponseEntity<Car>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Car>(car, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/addCar",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> createCar(@RequestBody Car car) throws Exception {
		Car savedCar = carService.create(car);
		return new ResponseEntity<Car>(savedCar, HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value = "/api/deleteCar",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> deleteCAr(@PathVariable("id")  Long id) throws Exception {
		Car deletedCar = carService.delete(id);
		return new ResponseEntity<Car>(deletedCar, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(
			value = "/api/updateCar",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> updateCar(@RequestBody Car car) throws Exception {
		Car updatedCar = carService.update(car);
		if (updatedCar == null)
			return new ResponseEntity<Car>(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<Car>(updatedCar, HttpStatus.OK);
	}
	
	
	

}
