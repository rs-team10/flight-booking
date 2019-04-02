package com.tim10.domain;

import java.util.Set;

public class Room {
	private Long id;
	private Integer floor;
	private Integer squareFootage;
	private Boolean hasBalcony;
	private RoomType roomType;
	private Set<RoomReservation> roomReservations;

	public Room() {
		super();
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

	public Boolean getHasBalcony() {
		return hasBalcony;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public Set<RoomReservation> getRoomReservations() {
		return roomReservations;
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

	public void setHasBalcony(Boolean hasBalcony) {
		this.hasBalcony = hasBalcony;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public void setRoomReservations(Set<RoomReservation> roomReservations) {
		this.roomReservations = roomReservations;
	}

}