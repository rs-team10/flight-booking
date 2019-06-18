package com.tim10.dto;

import java.util.Set;

import com.tim10.domain.Airline;
import com.tim10.domain.Destination;

public class AirlineDTO {

	private AirlineProfileDTO airlineProfileDTO;
	private Set<Destination> destinations;

	public AirlineDTO(Airline airline) {
		airlineProfileDTO = new AirlineProfileDTO(airline);
		destinations = airline.getBusinessLocations();
	}

	public AirlineProfileDTO getAirlineProfileDTO() {
		return airlineProfileDTO;
	}

	public void setAirlineProfileDTO(AirlineProfileDTO airlineProfileDTO) {
		this.airlineProfileDTO = airlineProfileDTO;
	}

	public AirlineDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<Destination> getDestinations() {
		return destinations;
	}

	public void setDestinations(Set<Destination> destinations) {
		this.destinations = destinations;
	}

}
