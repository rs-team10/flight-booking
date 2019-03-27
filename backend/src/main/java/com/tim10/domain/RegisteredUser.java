package com.tim10.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RegisteredUser extends User {

	private String phone;

	private String address;
	
	@JsonIgnore
	private String avatar;
	
	@JsonIgnore
	private String verificationCode;
	
	@JsonIgnore
	private Boolean isConfirmed;
	
	public RegisteredUser() {
		super();
		this.setRole(Role.REGISTERED_USER);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
}
