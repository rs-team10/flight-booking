
package com.tim10.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="FlightReservations")
public class FlightReservation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="passengerName")
	private String passengerFirstName;
	
	@Column(name="passengerLastName")
	private String passengerLastName;
	
	@Column(name="passportNumber")
	private String passportNumber;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Seat seat;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Review review;
	
	//NEMA POTREBE??????
	//private Reservation reservation;

	public FlightReservation() {
	}
	
	public FlightReservation(String firstName, String lastName, String passportNumber) {
		this.passengerFirstName = firstName;
		this.passengerLastName = lastName;
		this.passportNumber = passportNumber;
	}

	public Long getId() {
		return id;
	}

	public String getPassengerFirstName() {
		return passengerFirstName;
	}

	public String getPassengerLastName() {
		return passengerLastName;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public Seat getSeat() {
		return seat;
	}

	public Review getReview() {
		return review;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPassengerFirstName(String passengerFirstName) {
		this.passengerFirstName = passengerFirstName;
	}

	public void setPassengerLastName(String passengerLastName) {
		this.passengerLastName = passengerLastName;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public void setReview(Review review) {
		this.review = review;
	}
}