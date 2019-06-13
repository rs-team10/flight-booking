package com.tim10.dto;

import java.util.Set;

import com.tim10.domain.HotelAdmin;
import com.tim10.domain.Location;

public class NewHotelDTO {

	private Set<HotelAdmin> administrators;
	private Location location;
	private String name;

	public Set<HotelAdmin> getAdministrators() {
		return administrators;
	}

	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public void setAdministrators(Set<HotelAdmin> administrators) {
		this.administrators = administrators;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NewHotelDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
