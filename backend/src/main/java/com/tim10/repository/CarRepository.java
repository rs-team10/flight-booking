package com.tim10.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.tim10.domain.Car;

@Repository
public class CarRepository implements ICarRepository {


	
	private static AtomicLong counter = new AtomicLong();
	private final ConcurrentMap<Long, Car> cars = new  ConcurrentHashMap<Long, Car>();

	
	@Override
	public Collection<Car> findAll() {

		return this.cars.values();
	}
	@Override
	public Collection<Car> find(Car car) {
		Collection<Car> retCars = new ArrayList<Car>();
		
		String name = car.getName();
		String brand = car.getBrand() ;
		String model = car.getModel() ;
		int year = car.getYear();
		int seatsNumber = car.getSeatsNumber();
		String type = car.getType();
		double average = car.getAverage();
		
		if(name == null && brand == null && model == null && year == 0 
				&& seatsNumber == 0 && type == null && average == 0)
			return this.cars.values();

		if(name != null) {
			for( Car c : this.cars.values()) {
				if(c.getName().equals(name))
					retCars.add(c);
			}
		}
		if(brand != null) {
			for( Car c : this.cars.values()) {
				if(c.getBrand().equals(brand))
					retCars.add(c);
			}
		}
		if(model != null) {
			for( Car c : this.cars.values()) {
				if(c.getModel().equals(model))
					retCars.add(c);
			}
		}
		if(year != 0) {
			for( Car c : this.cars.values()) {
				if(c.getYear() == year)
					retCars.add(c);
			}
		}
		if(seatsNumber != 0) {
			for( Car c : this.cars.values()) {
				if(c.getSeatsNumber() == seatsNumber)
					retCars.add(c);
			}
		}
		if(type != null) {
			for( Car c : this.cars.values()) {
				if(c.getType().equals(type))
					retCars.add(c);
			}
		}
		if(average != 0) {
			for( Car c : this.cars.values()) {
				if(c.getAverage() == average)
					retCars.add(c);
			}
		}
		return retCars;
	}
	
	@Override
	public Car findById(Long id) {
		return cars.get(id);
	}
	
	@Override
	public Car create(Car car) {
	
		Long id = car.getId();
		if(id == null) {
			id = counter.incrementAndGet();
			car.setId(id);
		}
		this.cars.put(id, car);

		return car;
	}
	
	@Override
	public Car update(Car car) {
		Long id = car.getId();
		if(id != null) 
			if(cars.keySet().contains(id)) {
				Car c = this.cars.get(id);
				String name = car.getName();
				String brand = car.getBrand() ;
				String model = car.getModel() ;
				int year = car.getYear();
				int seatsNumber = car.getSeatsNumber();
				String type = car.getType();
				double average = car.getAverage();
				c.setName(name);
				c.setBrand(brand) ;
				c.setModel(model) ;
				c.setYear(year);
				c.setSeatsNumber(seatsNumber);
				c.setType(type);
				c.setAverage(average);
				return c;
			}

		return null;
	}
	@Override
	public Car delete(Long id) {
		Car c = this.cars.get(id);
		if(id != null) 
			if(cars.keySet().contains(id))
				this.cars.remove(id);
		return c;
	}
	
	
	
	
	
	
}
