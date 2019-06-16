package com.tim10.dto;

import com.tim10.domain.RequestStatus;

public class InvitationDTO {
	
	private String id;
	private String departure;
	private String destination;
	private String date;
	private String by;
	private RequestStatus status;
	
	public InvitationDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public String getDeparture() {
		return departure;
	}

	public String getDestination() {
		return destination;
	}

	public String getDate() {
		return date;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}
	
	
}
