package com.tim10.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.Hotel;
import com.tim10.repository.HotelRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;
	
	public List<Hotel> findAll(){
		return hotelRepository.findAll();
	}
	 
	public Hotel save(Hotel hotel) {
		return hotelRepository.save(hotel);
	}
	
	

}
