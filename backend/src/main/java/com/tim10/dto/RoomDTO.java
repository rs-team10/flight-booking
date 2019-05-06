package com.tim10.dto;

import com.tim10.domain.RoomType;

public class RoomDTO {
	
	int numberOfRooms;
	RoomType roomType;
	
	public RoomDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoomDTO(int numberOfRooms, RoomType roomType) {
		super();
		this.numberOfRooms = numberOfRooms;
		this.roomType = roomType;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
}
