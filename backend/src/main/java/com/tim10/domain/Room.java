package com.tim10.domain;

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
		
	//mappedBy room znaci da ce se foreign key nalaziti u tabeli u kojoj 
	//je deklarisan atribut koji se zove room (znaci bice u tabeli room reservations
	//@OneToMany(mappedBy="room", fetch=FetchType.LAZY)
	@OneToMany(fetch=FetchType.LAZY)
	private Set<RoomReservation> roomReservations;
	
	public Room() {
		super();
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