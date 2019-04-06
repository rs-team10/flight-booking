package com.tim10.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Hotels")
public class Hotel extends Company {
	
	@OneToMany(/*mappedBy="hotel",*/ cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Room> rooms = new HashSet<Room>();
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<RoomType> roomTypes;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private PriceList additionalServicesPriceList;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<QuickRoomReservation> quickRoomReservations;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
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