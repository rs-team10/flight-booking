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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="street", nullable = false)
	private String street;
	
	@Column(name="city")
	private String city;
	
	@Column(name="postalCode")
	private String postalCode;
	
	@Column(name="country")
	private String country;
	
	@Column(name="latitude")
	private Double latitude;
	
	@Column(name="longitude")
	private Double longitude;
	
	@Column(name="image")
	private String image;

	public Location() {
		super();
	}
	
	public Location(Long id, String street, String city, String postalCode, String country, Double latitude,
			Double longitude, String image) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.postalCode = postalCode;
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

	public String getPostalCode() {
		return postalCode;
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

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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