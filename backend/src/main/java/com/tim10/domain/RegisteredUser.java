package com.tim10.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RegisteredUser extends User {
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="address")
	private String address;
	
	@Column(name="bonusPoints")
	private Integer bonusPoints = 0;
	
	@Column(name="avatar")
	private String avatar;
	
	@Column(name="verificationCode")
	private String verificationCode;
	
	@Column(name="isConfirmed")
	private Boolean isConfirmed;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<Reservation> reservations;
	
	@JsonIgnore
	@OneToMany(mappedBy="sender", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
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