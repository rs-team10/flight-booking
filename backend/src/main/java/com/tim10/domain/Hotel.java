package com.tim10.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tim10.dto.NewHotelDTO;

@Entity
@Table(name="Hotels")
public class Hotel extends Company {
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "hotel_id")
	private Set<Room> rooms;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	private Set<RoomType> roomTypes;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private PriceList additionalServicesPriceList;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<QuickRoomReservation> quickRoomReservations;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER/*, mappedBy="hotel"*/)
	public Set<HotelAdmin> administrators;

	public Hotel() {
		rooms = new HashSet<Room>();
		roomTypes = new HashSet<RoomType>();
		additionalServicesPriceList = new PriceList();
		quickRoomReservations = new HashSet<QuickRoomReservation>();
		administrators = new HashSet<HotelAdmin>();
	}
	
	public Hotel(NewHotelDTO dto) {
		rooms = new HashSet<Room>();
		roomTypes = new HashSet<RoomType>();
		additionalServicesPriceList = new PriceList();
		quickRoomReservations = new HashSet<QuickRoomReservation>();
		this.setName(dto.getName());
		this.setLocation(dto.getLocation());
		administrators = dto.getAdministrators();
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