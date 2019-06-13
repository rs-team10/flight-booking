package com.tim10.dto;

import java.util.Set;

import com.tim10.domain.AirlineAdmin;
import com.tim10.domain.Location;

public class NewAirlineDTO {

	private Set<AirlineAdmin> administrators;
	private Location location;
	private String name;

	public NewAirlineDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<AirlineAdmin> getAdministrators() {
		return administrators;
	}

	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public void setAdministrators(Set<AirlineAdmin> administrators) {
		this.administrators = administrators;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

}
