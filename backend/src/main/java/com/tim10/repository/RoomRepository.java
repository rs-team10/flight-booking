package com.tim10.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.tim10.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	
	//List<Room> findByRoomTypeId(Long id);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	//@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="5000")})
	Optional<Room> findOneById(Long id);
}
