package com.tim10.domain.model;

public abstract class Company {
	
	private Long id;
	private String name;
	private String description;
	private Double averageFeedback;

	private Location location;

	public Company() {
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getAverageFeedback() {
		return averageFeedback;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAverageFeedback(Double averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}