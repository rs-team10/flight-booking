package com.tim10.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.Vehicle;
import com.tim10.repository.CarRepository;


@Service
public class CarService {
	
	@Autowired
	private CarRepository carRepository;
	
	
	public Collection<Vehicle> findAll(){
		return carRepository.findAll();
	}
	public Collection<Vehicle> find(Vehicle car){
		
		return carRepository.find(car);
	}
	public Vehicle findById(Long id) {
		return carRepository.findById(id);
	}
	public Vehicle create(Vehicle car) throws Exception {
		if(car.getId() != null)
			throw new Exception("Id has to be null when creating new entity");
		Vehicle savedUser = carRepository.create(car);
		return savedUser;
	}
	public Vehicle update(Vehicle car) {
		return carRepository.update(car);
	}
	public Vehicle delete(Long id) {
		return carRepository.delete( id);
	}
	
	

}
