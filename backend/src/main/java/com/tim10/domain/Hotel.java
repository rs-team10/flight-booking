package com.tim10.domain;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Hotel extends Company {
	
	@JsonIgnore
	private Set<Room> rooms;
	
	@JsonIgnore
	private Set<RoomType> roomTypes;
	
	@JsonIgnore
	private PriceList additionalServicesPriceList;
	
	@JsonIgnore
	private Set<QuickRoomReservation> quickRoomReservations;
	
	@JsonIgnore
	public Set<HotelAdmin> administrators;

	public Hotel() {
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public Set<RoomType> getRoomTypes() {
		return roomTypes;
	}

	public PriceList getAdditionalServicesPriceList() {
		return additionalServicesPriceList;
	}

	public Set<QuickRoomReservation> getQuickRoomReservations() {
		return quickRoomReservations;
	}

	public Set<HotelAdmin> getAdministrators() {
		return administrators;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public void setRoomTypes(Set<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
	}

	public void setAdditionalServicesPriceList(PriceList additionalServicesPriceList) {
		this.additionalServicesPriceList = additionalServicesPriceList;
	}

	public void setQuickRoomReservations(Set<QuickRoomReservation> quickRoomReservations) {
		this.quickRoomReservations = quickRoomReservations;
	}

	public void setAdministrators(Set<HotelAdmin> administrators) {
		this.administrators = administrators;
	}

}