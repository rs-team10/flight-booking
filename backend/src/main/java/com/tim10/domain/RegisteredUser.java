package com.tim10.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

public class RegisteredUser extends User {
	
	private String phone;
	
	private String address;
	
	@JsonIgnore
	private Integer bonusPoints;
	
	@JsonIgnore
	private String avatar;
	
	@JsonIgnore
	private String verificationCode;
	
	@JsonIgnore
	private Boolean isConfirmed;
	
	@JsonIgnore
	private Set<Reservation> reservations;
	
	@JsonIgnore
	private Set<Friendship> friendships;

	public RegisteredUser() {
		super();
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public Integer getBonusPoints() {
		return bonusPoints;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public Boolean getIsConfirmed() {
		return isConfirmed;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public Set<Friendship> getFriendships() {
		return friendships;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setBonusPoints(Integer bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public void setFriendships(Set<Friendship> friendships) {
		this.friendships = friendships;
	}
}