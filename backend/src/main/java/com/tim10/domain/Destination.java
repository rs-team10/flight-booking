package com.tim10.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Destinations")
public class Destination {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="airportName")
	private String airportName;
	
	@Column(name="airportCode", nullable=false)
	private String airportCode;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Location location;
	
	@Column(name="image")
	private String image;
	
	public Destination() {
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAirportName() {
		return airportName;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public String getImage() {
		return image;
	}

	public Location getLocation() {
		return location;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}