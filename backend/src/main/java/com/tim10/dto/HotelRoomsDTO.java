package com.tim10.dto;

import java.util.HashSet;
import java.util.Set;

import com.tim10.domain.Hotel;
import com.tim10.domain.Location;
import com.tim10.domain.PriceList;
import com.tim10.domain.Room;
import com.tim10.domain.RoomType;

public class HotelRoomsDTO {

	private Long id;
	private String name;
	private String description;
	private Double averageFeedback;
	private Location location;
	private Set<RoomType> roomTypes;
	private Set<RoomDTO> rooms = new HashSet<RoomDTO>();
	private PriceList additionalServicesPriceList;

	public HotelRoomsDTO() {
		super();
	}
	
	public HotelRoomsDTO(Hotel hotel) {
		this.id = hotel.getId();
		this.name = hotel.getName();
		this.description = hotel.getDescription();
		this.averageFeedback = hotel.getAverageFeedback();
		this.location = hotel.getLocation();
		this.roomTypes = hotel.getRoomTypes();
		this.additionalServicesPriceList = hotel.getAdditionalServicesPriceList();
		for(Room room : hotel.getRooms())
			this.rooms.add(new RoomDTO(room));
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

	public Set<RoomDTO> getRooms() {
		return rooms;
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

	public void setRooms(Set<RoomDTO> rooms) {
		this.rooms = rooms;
	}

	public void setAdditionalServicesPriceList(PriceList additionalServicesPriceList) {
		this.additionalServicesPriceList = additionalServicesPriceList;
	}

}
