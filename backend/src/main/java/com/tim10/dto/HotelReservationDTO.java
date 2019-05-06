package com.tim10.dto;

import java.util.Set;

import com.tim10.domain.Hotel;
import com.tim10.domain.Location;
import com.tim10.domain.PriceList;
import com.tim10.domain.RoomType;

public class HotelReservationDTO {
	
	private Long id;
	private String name;
	private String description;
	private Double averageFeedback;
	private Location location;
	private Set<RoomType> roomTypes;
	private PriceList additionalServicesPriceList;
	
	public HotelReservationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public HotelReservationDTO(Hotel hotel) {
		this.id = hotel.getId();
		this.name = hotel.getName();
		this.description = hotel.getDescription();
		this.averageFeedback = hotel.getAverageFeedback();
		this.location = hotel.getLocation();
		this.roomTypes = hotel.getRoomTypes();
		this.additionalServicesPriceList = hotel.getAdditionalServicesPriceList();
	}
	
	public HotelReservationDTO(Long id, String name, String description, Double averageFeedback, Location location,
			Set<RoomType> roomTypes, PriceList additionalServicesPriceList) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.averageFeedback = averageFeedback;
		this.location = location;
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

	public void setRoomTypes(Set<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
	}

	public void setAdditionalServicesPriceList(PriceList additionalServicesPriceList) {
		this.additionalServicesPriceList = additionalServicesPriceList;
	}
	
	
	
}
