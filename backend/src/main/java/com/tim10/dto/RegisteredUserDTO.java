package com.tim10.dto;

import com.tim10.domain.RegisteredUser;

public class RegisteredUserDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String phone;
	private String address;
	
	public RegisteredUserDTO() {
	}

	public RegisteredUserDTO(Long id, String firstName, String lastName, String username, String email, String phone,
			String address) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	public RegisteredUserDTO(RegisteredUser currentUser) {
		this.id = currentUser.getId();
		this.firstName = currentUser.getFirstName();
		this.lastName = currentUser.getLastName();
		this.username = currentUser.getUsername();
		this.email = currentUser.getEmail();
		this.phone = currentUser.getPhone();
		this.address = currentUser.getAddress();
	}
	
	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
