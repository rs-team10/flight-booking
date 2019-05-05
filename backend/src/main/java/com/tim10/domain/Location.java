package com.tim10.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Locations")
public class Location implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="street", nullable = false)
	private String street;
	
	@Column(name="city")
	private String city;
	
	@Column(name="country")
	private String country;
	
	@Column(name="latitude")
	private Double latitude;
	
	@Column(name="longitude")
	private Double longitude;
	
	@Column(name="formattedAddress")
	private String formattedAddress;
	
	@Column(name="image")
	private String image;

	public Location() {
		super();
	}
	
	public Location(Long id, String street, String city, String formattedAddress, String country, Double latitude,
			Double longitude, String image) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.formattedAddress = formattedAddress;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.image = image;
	}

	public Long getId() {
		return id;
	}
	
	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getformattedAddress() {
		return formattedAddress;
	}

	public String getCountry() {
		return country;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getImage() {
		return image;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setformattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setImage(String image) {
		this.image = image;
	}
}