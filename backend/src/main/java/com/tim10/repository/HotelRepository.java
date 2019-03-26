package com.tim10.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.tim10.domain.Hotel;

@Repository
public class HotelRepository {

	private static AtomicLong counter = new AtomicLong();
	private final ConcurrentMap<Long, Hotel> hotels = new ConcurrentHashMap<Long, Hotel>();

	public Collection<Hotel> findAll(){
		return this.hotels.values();
	}
	
	public Hotel create(Hotel hotel) {
		Long id = hotel.getId();
		if(id == null) {
			id = counter.incrementAndGet();
			hotel.setId(id);
		}
		this.hotels.put(id, hotel);
		return hotel;
	}

}
