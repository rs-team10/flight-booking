package com.tim10.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Reservations")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Reservation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="isHost")
	private Boolean isHost;
	
	@Column(name="invitationCode")
	private String invitationCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")				
	private RequestStatus status;
	
	@Column(name="distance")
	private Integer distance;
	
	@Column(name="discount")
	private Double discount;
	
	@Column(name="usedDiscount")
	private Boolean usedDiscount;
	
	@Column(name="hasPassed")
	private Boolean hasPassed;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private RegisteredUser registeredUser;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private GroupReservation groupReservation;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private FlightReservation flightReservation;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private VehicleReservation vehicleReservation;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private RoomReservation roomReservation;

	public Reservation() {
		super();
	}

	public Long getId() {
		return id;
	}

	public Boolean getIsHost() {
		return isHost;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public Integer getDistance() {
		return distance;
	}

	public Double getDiscount() {
		return discount;
	}

	public Boolean getUsedDiscount() {
		return usedDiscount;
	}

	public Boolean getHasPassed() {
		return hasPassed;
	}

	public FlightReservation getFlightReservation() {
		return flightReservation;
	}

	public RegisteredUser getRegisteredUser() {
		return registeredUser;
	}

	public GroupReservation getGroupReservation() {
		return groupReservation;
	}

	public VehicleReservation getVehicleReservation() {
		return vehicleReservation;
	}

	public RoomReservation getRoomReservation() {
		return roomReservation;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIsHost(Boolean isHost) {
		this.isHost = isHost;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public void setUsedDiscount(Boolean usedDiscount) {
		this.usedDiscount = usedDiscount;
	}

	public void setHasPassed(Boolean hasPassed) {
		this.hasPassed = hasPassed;
	}

	public void setFlightReservation(FlightReservation flightReservation) {
		this.flightReservation = flightReservation;
	}

	public void setRegisteredUser(RegisteredUser registeredUser) {
		this.registeredUser = registeredUser;
	}

	public void setGroupReservation(GroupReservation groupReservation) {
		this.groupReservation = groupReservation;
	}

	public void setVehicleReservation(VehicleReservation vehicleReservation) {
		this.vehicleReservation = vehicleReservation;
	}

	public void setRoomReservation(RoomReservation roomReservation) {
		this.roomReservation = roomReservation;
	}

}