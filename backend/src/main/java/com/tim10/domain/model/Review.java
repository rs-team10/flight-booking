package com.tim10.domain.model;

public class Review {
	private Long id;
	private Integer companyFeedback;
	private Integer serviceFeedback;
	private RoomReservation roomReservation;
	private FlightReservation flightReservation;
	private VehicleReservation vehicleReservation;

	public Review() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Integer getCompanyFeedback() {
		return companyFeedback;
	}

	public Integer getServiceFeedback() {
		return serviceFeedback;
	}

	public RoomReservation getRoomReservation() {
		return roomReservation;
	}

	public FlightReservation getFlightReservation() {
		return flightReservation;
	}

	public VehicleReservation getVehicleReservation() {
		return vehicleReservation;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCompanyFeedback(Integer companyFeedback) {
		this.companyFeedback = companyFeedback;
	}

	public void setServiceFeedback(Integer serviceFeedback) {
		this.serviceFeedback = serviceFeedback;
	}

	public void setRoomReservation(RoomReservation roomReservation) {
		this.roomReservation = roomReservation;
	}

	public void setFlightReservation(FlightReservation flightReservation) {
		this.flightReservation = flightReservation;
	}

	public void setVehicleReservation(VehicleReservation vehicleReservation) {
		this.vehicleReservation = vehicleReservation;
	}

}