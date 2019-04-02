package com.tim10.domain.model;

public class Destination {
	
	private Long id;
	private String name;
	private String airportName;
	private String airportCode;
	private String image;

	private Location location;

	public Destination() {
	}

	public Long getId() {
		return id;
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

	public String getImage() {
		return image;
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

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}