package com.tim10.domain;

import java.io.Serializable;



@SuppressWarnings("serial")
public class UserTokenState implements Serializable {
	/**
	 * 
	 */
	private String accessToken;
	private Long expiresIn;
	private Role role;

	public UserTokenState() {
		this.accessToken = null;
		this.expiresIn = null;
		this.setRole(null);
	}

	public UserTokenState(String accessToken, long expiresIn, Role role) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.setRole(role);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}