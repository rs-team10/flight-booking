package com.tim10.dto;

import java.util.ArrayList;
import java.util.List;

public class RoomsDTO {
	
	List<RoomDTO> rooms;

	public RoomsDTO() {
		super();
		rooms = new ArrayList<>();
	}

	public RoomsDTO(List<RoomDTO> rooms) {
		super();
		this.rooms = rooms;
	}

	public List<RoomDTO> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomDTO> rooms) {
		this.rooms = rooms;
	}
	
	
	
	
}
