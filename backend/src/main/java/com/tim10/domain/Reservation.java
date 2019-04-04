package com.tim10.domain;

public class Reservation {
	private Long id;
	private Boolean isHost;
	private String invitationCode;
	private RequestStatus status;
	private Integer distance;
	private Double discount;
	private Boolean usedDiscount;
	private Boolean hasPassed;
	private FlightReservation flightReservation;
	private RegisteredUser registeredUser;
	private GroupReservation groupReservation;
	private VehicleReservation vehicleReservation;
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