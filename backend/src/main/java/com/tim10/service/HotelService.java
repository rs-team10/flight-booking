package com.tim10.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.Hotel;
import com.tim10.repository.HotelRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;
	
	public Collection<Hotel> findAll(){
		Collection<Hotel> hotels = hotelRepository.findAll();
		return hotels;
	}
	
	public Hotel create(Hotel hotel) throws Exception {
		if(hotel.getId() != null) {
			throw new Exception("New hotel id must be null");
		}
		for(Hotel existingHotel : findAll()) {
			if(existingHotel.getName().toLowerCase().equals(hotel.getName().toLowerCase())) {
				throw new Exception("Hotel with that name already exists");
			}
		}
		Hotel savedHotel = hotelRepository.create(hotel);
		return savedHotel;
	}

}
