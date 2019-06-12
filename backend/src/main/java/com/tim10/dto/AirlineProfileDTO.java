package com.tim10.dto;

import com.tim10.domain.Airline;

public class AirlineProfileDTO {
	
	private String name;
	private String description;
	private LocationDTO location;
	private Double averageFeedback;
	
	public AirlineProfileDTO() {
		super();
	}
	
	public AirlineProfileDTO(Airline a) {
		super();
		this.name = a.getName();
		this.description = a.getDescription();
		this.location = new LocationDTO(a.getLocation());
		this.averageFeedback = (a.getAverageFeedback() == null) ? 0 : a.getAverageFeedback();
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public LocationDTO getLocation() {
		return location;
	}
	public Double getAverageFeedback() {
		return averageFeedback;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setLocation(LocationDTO location) {
		this.location = location;
	}
	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}
}
