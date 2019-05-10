package com.tim10.dto;

import com.tim10.domain.Room;
import com.tim10.domain.RoomType;

public class RoomDTO {

	private Long id;
	private Integer floor;
	private Integer roomNumber;
	private RoomType roomType;
	
	private boolean isReserved;

	public RoomDTO(Room room) {
		this.id = room.getId();
		this.floor = room.getFloor();
		this.roomNumber = room.getRoomNumber();
		this.roomType = room.getRoomType();
		this.isReserved = room.getRoomReservations().isEmpty() ? false : true;
	}

	public RoomDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public Long getId() {
		return id;
	}

	public Integer getFloor() {
		return floor;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public boolean isReserved() {
		return isReserved;
	}

}
