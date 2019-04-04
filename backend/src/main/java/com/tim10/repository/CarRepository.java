package com.tim10.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.tim10.domain.Vehicle;

@Repository
public class CarRepository implements ICarRepository {


	
	private static AtomicLong counter = new AtomicLong();
	private final ConcurrentMap<Long, Vehicle> cars = new  ConcurrentHashMap<Long, Vehicle>();

	
	@Override
	public Collection<Vehicle> findAll() {

		return this.cars.values();
	}
	@Override
	public Collection<Vehicle> find(Vehicle car) {
		Collection<Vehicle> retCars = new ArrayList<Vehicle>();
		
		return retCars;
	}
	
	@Override
	public Vehicle findById(Long id) {
		return cars.get(id);
	}
	
	@Override
	public Vehicle create(Vehicle car) {
	
		Long id = car.getId();
		if(id == null) {
			id = counter.incrementAndGet();
			car.setId(id);
		}
		this.cars.put(id, car);

		return car;
	}
	
	@Override
	public Vehicle update(Vehicle car) {

		return null;
	}
	@Override
	public Vehicle delete(Long id) {
		Vehicle c = this.cars.get(id);
		if(id != null) 
			if(cars.keySet().contains(id))
				this.cars.remove(id);
		return c;
	}

}
