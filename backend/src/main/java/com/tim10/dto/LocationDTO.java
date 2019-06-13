package com.tim10.dto;

import com.tim10.domain.Location;

public class LocationDTO {
	
	private String formattedAddress;
	private Double latitude;
	private Double longitude;
	
	public LocationDTO() {
	}

	public LocationDTO(Location l) {
		super();
		this.formattedAddress = l.getformattedAddress();
		this.latitude = l.getLatitude();
		this.longitude = l.getLongitude();
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
