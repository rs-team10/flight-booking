
package com.tim10.domain.model;

public class FlightReservation {
	
	private Long id;
	private String passengerName;
	private Long passportNumber;

	private Seat seat;
	private Review review;
	private Reservation reservation;

	public FlightReservation() {
	}

	public Long getId() {
		return id;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public Long getPassportNumber() {
		return passportNumber;
	}

	public Seat getSeat() {
		return seat;
	}

	public Review getReview() {
		return review;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public void setPassportNumber(Long passportNumber) {
		this.passportNumber = passportNumber;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

}