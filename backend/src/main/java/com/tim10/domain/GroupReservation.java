package com.tim10.domain;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="GroupReservations")
public class GroupReservation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="creationDateTime")
	private LocalDateTime creationDateTime;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
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