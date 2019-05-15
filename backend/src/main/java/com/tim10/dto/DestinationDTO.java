package com.tim10.dto;

import com.tim10.domain.Destination;

public class DestinationDTO {
	
	private String name;
	private String airportName;
	private String airportCode;
	private LocationDTO location;
	
	public DestinationDTO() {
		super();
	}
	
	public DestinationDTO(Destination d) {
		super();
		this.name = d.getName();
		this.airportName = d.getAirportName();
		this.airportCode = d.getAirportCode();
		this.location = new LocationDTO(d.getLocation());
	}

	public String getName() {
		return name;
	}

	public String getAirportName() {
		return airportName;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}
}
