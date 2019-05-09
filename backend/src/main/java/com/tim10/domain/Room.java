package com.tim10.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="Rooms")
public class Room {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="floor")
	private Integer floor;
	
	@Column(name="squareFootage")
	private Integer squareFootage;
	
	@Column(name="hasBalcony")
	private Boolean hasBalcony;
	
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@ManyToOne(fetch = FetchType.EAGER)
	private RoomType roomType;
		
	//@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@OneToMany(mappedBy="room", fetch=FetchType.LAZY)
	private Set<RoomReservation> roomReservations;
	
	public boolean isReserved(Date checkInDate, Date checkOutDate) {
		for(RoomReservation roomReservation: this.roomReservations) {
			if( (roomReservation.getDateFrom().compareTo(checkInDate) <= 0)
					&& (roomReservation.getDateTo().compareTo(checkInDate) > 0) ) {
				return true;
			}
			else if( (roomReservation.getDateFrom().compareTo(checkOutDate) < 0)
					&& (roomReservation.getDateTo().compareTo(checkOutDate) >= 0) ) {
				return true;
			}
		}
		return false;
	}
	
	public Room() {
		super();
		this.roomReservations = new HashSet<>();
	}

	public Long getId() {
		return id;
	}

	public Integer getFloor() {
		return floor;
	}

	public Integer getSquareFootage() {
		return squareFootage;
	}

	public Boolean getHasBalcony() {
		return hasBalcony;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public Set<RoomReservation> getRoomReservations() {
		return roomReservations;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public void setSquareFootage(Integer squareFootage) {
		this.squareFootage = squareFootage;
	}

	public void setHasBalcony(Boolean hasBalcony) {
		this.hasBalcony = hasBalcony;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public void setRoomReservations(Set<RoomReservation> roomReservations) {
		this.roomReservations = roomReservations;
	}

}