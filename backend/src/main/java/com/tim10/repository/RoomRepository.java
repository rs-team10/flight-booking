package com.tim10.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	
	List<Room> findByRoomTypeId(Long id);
}
