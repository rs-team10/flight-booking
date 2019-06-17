package com.tim10.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.tim10.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	
	//List<Room> findByRoomTypeId(Long id);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Room> findOneById(Long id);
}
