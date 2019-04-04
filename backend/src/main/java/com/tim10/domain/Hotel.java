package com.tim10.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Hotel extends Company {
	
	@Transient
	private Set<Room> rooms;
	
	@Transient
	private Set<RoomType> roomTypes;
	
	@Transient
	private PriceList additionalServicesPriceList;
	
	@Transient
	private Set<QuickRoomReservation> quickRoomReservations;
	
	@Transient
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