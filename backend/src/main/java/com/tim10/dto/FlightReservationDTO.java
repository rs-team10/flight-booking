package com.tim10.dto;

import java.util.ArrayList;
import java.util.List;

public class FlightReservationDTO {
	
	private Long flightId;
	private List<SeatReservationDTO> seatReservationDTOList;
	
	public FlightReservationDTO() {
		super();
		seatReservationDTOList = new ArrayList<>();
	}

	public Long getFlightId() {
		return flightId;
	}

	public List<SeatReservationDTO> getSeatReservationDTOList() {
		return seatReservationDTOList;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public void setSeatReservationDTOList(List<SeatReservationDTO> seatReservationDTOList) {
		this.seatReservationDTOList = seatReservationDTOList;
	}

}
