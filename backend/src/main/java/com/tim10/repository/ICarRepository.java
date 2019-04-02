package com.tim10.repository;

import java.util.Collection;

import com.tim10.domain.Car;

public interface ICarRepository {
	
	
	Collection<Car> findAll();
	Collection<Car> find(Car car);
	Car findById(Long id);
	Car create(Car car);
	Car update(Car car);
	Car delete(Long id);
	

}
