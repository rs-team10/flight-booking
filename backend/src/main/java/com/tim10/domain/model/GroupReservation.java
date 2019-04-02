package com.tim10.domain.model;

import java.time.LocalDateTime;
import java.util.Set;

public class GroupReservation {
	
	private Long id;
	private LocalDateTime creationDateTime;

	private Set<Reservation> reservations;

	public GroupReservation() {
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

}