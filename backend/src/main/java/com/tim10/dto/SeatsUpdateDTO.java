package com.tim10.dto;

import java.util.ArrayList;
import java.util.List;

public class SeatsUpdateDTO {
	
	private List<SeatDTO> updatedSeatsList;
	private Long flightId;
	
	public SeatsUpdateDTO() {
		super();
		this.updatedSeatsList = new ArrayList<SeatDTO>();
	}

	public List<SeatDTO> getUpdatedSeatsList() {
		return updatedSeatsList;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setUpdatedSeatsList(List<SeatDTO> updatedSeatsList) {
		this.updatedSeatsList = updatedSeatsList;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	
	
}
