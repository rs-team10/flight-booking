package com.tim10.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tim10.domain.Hotel;
import com.tim10.dto.HotelDTO;
import com.tim10.repository.HotelRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;
	
	public List<Hotel> findAll(){
		return hotelRepository.findAll();
	}
	
	public Page<Hotel> findAll(Pageable page) {
		return hotelRepository.findAll(page);
	}
	 
	public Hotel save(Hotel hotel) {
		return hotelRepository.save(hotel);
	}
	
	public boolean hotelExists(Hotel hotel){
		if(findOneByName(hotel.getName()) == null) {
			return true;
		}
		return false;
	}
	
	public Hotel findOneByName(String name) {
		return hotelRepository.findOneByName(name);
	}
	
	public Optional<Hotel> findOne(Long id) {
		return hotelRepository.findById(id);
	}
	
	public List<Hotel> findByParameter(String parameter){
		return hotelRepository.findByParameter(parameter);
	}

}
