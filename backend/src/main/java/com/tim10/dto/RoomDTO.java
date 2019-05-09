package com.tim10.dto;

import com.tim10.domain.Room;
import com.tim10.domain.RoomType;

public class RoomDTO {

	private Long id;
	private Integer floor;
	private Integer squareFootage;
	private boolean hasBalcony;
	private RoomType roomType;
	
	private boolean isReserved;

	public RoomDTO(Room room) {
		this.id = room.getId();
		this.floor = room.getFloor();
		this.squareFootage = room.getSquareFootage();
		this.hasBalcony = room.getHasBalcony() == null ? false : true;
		this.roomType = room.getRoomType();
		this.isReserved = room.getRoomReservations().isEmpty() ? false : true;
	}

	public RoomDTO(Long id, Integer floor, Integer squareFootage, boolean hasBalcony, RoomType roomType) {
		super();
		this.id = id;
		this.floor = floor;
		this.squareFootage = squareFootage;
		this.hasBalcony = hasBalcony;
		this.roomType = roomType;
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

	public void setSquareFootage(Integer squareFootage) {
		this.squareFootage = squareFootage;
	}

	public void setHasBalcony(boolean hasBalcony) {
		this.hasBalcony = hasBalcony;
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

	public Integer getSquareFootage() {
		return squareFootage;
	}

	public boolean isHasBalcony() {
		return hasBalcony;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public boolean isReserved() {
		return isReserved;
	}

}
