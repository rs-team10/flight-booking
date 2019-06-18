package com.tim10.domain;

import java.io.Serializable;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "Rooms")
public class Room implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "roomNumber")
	private Integer roomNumber;

	@Column(name = "floor")
	private Integer floor;

	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@ManyToOne(fetch = FetchType.EAGER)
	private RoomType roomType;

	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
	private Set<RoomReservation> roomReservations;

	// @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	// @JoinColumn(name="hotel_id")
	private Hotel hotel;

	public boolean isReserved(Date checkInDate, Date checkOutDate) {
		for (RoomReservation roomReservation : this.roomReservations) {
			if (((roomReservation.getDateFrom().compareTo(checkInDate) <= 0)
					&& (roomReservation.getDateTo().compareTo(checkInDate) > 0))
					|| ((roomReservation.getDateFrom().compareTo(checkOutDate) < 0)
							&& (roomReservation.getDateTo().compareTo(checkOutDate) >= 0))) {
				return true;
			}
		}
		return false;
	}

	public Room() {
		super();
		this.roomReservations = new HashSet<>();
		// this.hotel = new Hotel();
	}

	public Long getId() {
		return id;
	}

	public Integer getFloor() {
		return floor;
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

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public void setRoomReservations(Set<RoomReservation> roomReservations) {
		this.roomReservations = roomReservations;
	}
	
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

}