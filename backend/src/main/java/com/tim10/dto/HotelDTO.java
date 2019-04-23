package com.tim10.dto;

import java.util.Set;

import com.tim10.domain.Hotel;
import com.tim10.domain.Location;
import com.tim10.domain.PriceList;
import com.tim10.domain.Room;
import com.tim10.domain.RoomType;

public class HotelDTO {

	private Long id;
	private String name;
	private String description;
	private Double averageFeedback;
	private Location location;
	private Set<Room> rooms;
	private Set<RoomType> roomTypes;
	private PriceList additionalServicesPriceList;

	public HotelDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public HotelDTO(Hotel hotel) {
		this.id = hotel.getId();
		this.name = hotel.getName();
		this.description = hotel.getDescription();
		this.averageFeedback = hotel.getAverageFeedback();
		this.location = hotel.getLocation();
		this.rooms = hotel.getRooms();
		this.roomTypes = hotel.getRoomTypes();
		this.additionalServicesPriceList = hotel.getAdditionalServicesPriceList();
	}
	
	public HotelDTO(Long id, String name, String description, Double averageFeedback, Location location,
			Set<Room> rooms, Set<RoomType> roomTypes, PriceList additionalServicesPriceList) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.averageFeedback = averageFeedback;
		this.location = location;
		this.rooms = rooms;
		this.roomTypes = roomTypes;
		this.additionalServicesPriceList = additionalServicesPriceList;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getAverageFeedback() {
		return averageFeedback;
	}

	public Location getLocation() {
		return location;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	public void setLocation(Location location) {
		this.location = location;
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

}
