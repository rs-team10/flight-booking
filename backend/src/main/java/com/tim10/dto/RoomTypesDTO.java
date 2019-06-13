package com.tim10.dto;

import com.tim10.domain.RoomType;

public class RoomTypesDTO {
	
	private int numberOfRooms;
	private RoomType roomType;
	
	public RoomTypesDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoomTypesDTO(int numberOfRooms, RoomType roomType) {
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
