package com.tim10.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reviews")
public class Review implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="companyFeedback", nullable=true)
	private Integer companyFeedback;
	
	@Column(name="serviceFeedback", nullable=true)
	private Integer serviceFeedback;
	
	
	//OVO NEMA POTREBE DA BUDE TU
	
//	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
//	private RoomReservation roomReservation;
//	
//	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
//	private FlightReservation flightReservation;
//	
//	@Transient
//	private VehicleReservation vehicleReservation;

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

//	public RoomReservation getRoomReservation() {
//		return roomReservation;
//	}
//
//	public FlightReservation getFlightReservation() {
//		return flightReservation;
//	}
//
//	public VehicleReservation getVehicleReservation() {
//		return vehicleReservation;
//	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCompanyFeedback(Integer companyFeedback) {
		this.companyFeedback = companyFeedback;
	}

	public void setServiceFeedback(Integer serviceFeedback) {
		this.serviceFeedback = serviceFeedback;
	}

//	public void setRoomReservation(RoomReservation roomReservation) {
//		this.roomReservation = roomReservation;
//	}
//
//	public void setFlightReservation(FlightReservation flightReservation) {
//		this.flightReservation = flightReservation;
//	}
//
//	public void setVehicleReservation(VehicleReservation vehicleReservation) {
//		this.vehicleReservation = vehicleReservation;
//	}

}