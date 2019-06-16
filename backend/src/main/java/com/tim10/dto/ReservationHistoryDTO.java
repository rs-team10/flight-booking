package com.tim10.dto;

import java.util.Date;

public class ReservationHistoryDTO {
	
	
	private Long reservationId;
    
	private Long flightId; 
	private String departureName;  
	private String destinationName; 
	private Date departureDate;   
	private Long airlineId; 

	private Long roomTypeId;  
	private Long hotelId;    

	private Long vehicleId;  
	private Long rentACarId;
	
	public ReservationHistoryDTO() {

	} 
	
	public ReservationHistoryDTO(Long reservationId, Long flightId, String departureName, String destinationName,
			Date departureDate, Long airlineId, Long roomTypeId, Long hotelId, Long vehicleId, Long rentACarId) {
		super();
		this.reservationId = reservationId;
		this.flightId = flightId;
		this.departureName = departureName;
		this.destinationName = destinationName;
		this.departureDate = departureDate;
		this.airlineId = airlineId;
		this.roomTypeId = roomTypeId;
		this.hotelId = hotelId;
		this.vehicleId = vehicleId;
		this.rentACarId = rentACarId;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public Long getFlightId() {
		return flightId;
	}

	public String getDepartureName() {
		return departureName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public Long getAirlineId() {
		return airlineId;
	}

	public Long getRoomTypeId() {
		return roomTypeId;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public Long getRentACarId() {
		return rentACarId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public void setDepartureName(String departureName) {
		this.departureName = departureName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}

	public void setRoomTypeId(Long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public void setRentACarId(Long rentACarId) {
		this.rentACarId = rentACarId;
	} 

	
	
	
}
