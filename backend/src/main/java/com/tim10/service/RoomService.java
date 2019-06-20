package com.tim10.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim10.domain.Room;
import com.tim10.repository.RoomRepository;

@Service
public class RoomService {
	
	@Autowired 
	private RoomRepository roomRepository;
	
//	public List<Room> getRooms(Long id){
//		return roomRepository.findByRoomTypeId(id);
//	}
	
	public Optional<Room> getRoom(Long id) {
		return roomRepository.findById(id);
	}
	
	
}
