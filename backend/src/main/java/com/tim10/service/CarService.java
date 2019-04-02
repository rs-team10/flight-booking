package com.tim10.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.Car;
import com.tim10.repository.CarRepository;


@Service
public class CarService {
	
	@Autowired
	private CarRepository carRepository;
	
	
	public Collection<Car> findAll(){
		return carRepository.findAll();
	}
	public Collection<Car> find(Car car){
		
		return carRepository.find(car);
	}
	public Car findById(Long id) {
		return carRepository.findById(id);
	}
	public Car create(Car car) throws Exception {
		if(car.getId() != null)
			throw new Exception("Id has to be null when creating new entity");
		Car savedUser = carRepository.create(car);
		return savedUser;
	}
	public Car update(Car car) {
		return carRepository.update(car);
	}
	public Car delete(Long id) {
		return carRepository.delete( id);
	}
	
	

}
