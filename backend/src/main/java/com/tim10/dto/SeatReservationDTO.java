package com.tim10.dto;

public class SeatReservationDTO {
	
	private Long seatId;
	private Long userId;
	private String firstName;
	private String lastName;
	private String passportNumber;
	
	public SeatReservationDTO() {
		super();
	}

	public Long getSeatId() {
		return seatId;
	}

	public Long getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
}
