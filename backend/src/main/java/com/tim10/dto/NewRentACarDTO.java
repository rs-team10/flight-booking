package com.tim10.dto;

import java.util.Set;

import com.tim10.domain.Location;
import com.tim10.domain.RentACarAdmin;

public class NewRentACarDTO {

	private Set<RentACarAdmin> administrators;
	private Location location;
	private String name;

	public NewRentACarDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<RentACarAdmin> getAdministrators() {
		return administrators;
	}

	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public void setAdministrators(Set<RentACarAdmin> administrators) {
		this.administrators = administrators;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

}
