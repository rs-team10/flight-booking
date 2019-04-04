package com.tim10.dto;

import com.tim10.domain.Hotel;
import com.tim10.domain.Location;

public class HotelDTO {

	private Long id;
	private String name;
	private String description;
	private Double averageFeedback;
	private Location location;

	public HotelDTO() {
	}
	
	public HotelDTO(Hotel hotel) {
		this(hotel.getId(), hotel.getName(), hotel.getDescription(),
				hotel.getAverageFeedback(), hotel.getLocation());
	}
	
	public HotelDTO(Long id, String name, String description, Double averageFeedback, Location location) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.averageFeedback = averageFeedback;
		this.location = location;
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

}
