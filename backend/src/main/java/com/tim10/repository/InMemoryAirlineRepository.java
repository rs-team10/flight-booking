//package com.tim10.repository;
//
//import java.util.Collection;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.atomic.AtomicLong;
//
//import org.springframework.stereotype.Repository;
//
//import com.tim10.domain.Airline;
//
//@Repository
//public class InMemoryAirlineRepository implements AirlineRepository {
//	
//	private static AtomicLong counter = new AtomicLong();
//	
//	private final ConcurrentMap<Long, Airline> airlines = new ConcurrentHashMap<Long, Airline>();
//	
//	@Override
//	public Collection<Airline> findAll() {
//		return this.airlines.values();
//	}
//
//	@Override
//	public Airline create(Airline airline) {
//		Long id = airline.getId();
//		if(id == null) {
//			id = counter.incrementAndGet();
//			airline.setId(id);
//		}
//		this.airlines.put(id, airline);
//		return airline;
//	}
//
//	@Override
//	public Airline findByName(String name) {
//		Airline searchedAirline = null;
//		for(Airline airline : airlines.values())
//			if(airline.getName().equals(name))
//				searchedAirline = airline;
//		return searchedAirline;
//	}
//
//	@Override
//	public Airline findById(Long id) {
//		return this.airlines.get(id);
//	}
//
//	@Override
//	public Airline update(Airline airline) {
//		Long id = airline.getId();
//		if(id != null) {
//			this.airlines.put(id, airline);
//		}
//		return airline;
//	}
//}
