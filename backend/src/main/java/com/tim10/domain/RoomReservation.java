package com.tim10.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="RoomReservations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class RoomReservation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="dateFrom", nullable=false)
	@Type(type="date")
	private Date dateFrom;
	
	@Column(name="dateTo", nullable=false)
	@Type(type="date")
	private Date dateTo;
	
	//cena bez popusta, za brzu rezervaciju potrebno dinamicki racunati cenu sa popustom
	@Column(name="totalPrice", nullable=false)
	private BigDecimal totalPrice;
	
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<PriceListItem> additionalServices;
	
	//DA LI JE POTREBNA REFERENCA NA REZERVACIJU???????
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Reservation reservation;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Review review;
	
	@JsonIgnore
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="room_id")
	private Room room;
	
	@Version
	private Long version;
	
	public RoomReservation() {
		super();
		this.dateFrom = new Date();
		this.dateTo = new Date();
		this.additionalServices = new HashSet<>();
		this.review = new Review();
	}
	
	public RoomReservation(Date dateFrom, Date dateTo, BigDecimal totalPrice,
			Set<PriceListItem> additionalServices, Room room) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.totalPrice = totalPrice;
		this.additionalServices = additionalServices;
		this.room = room;
	}
	

	public Long getId() {
		return id;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public Set<PriceListItem> getAdditionalServices() {
		return additionalServices;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public Review getReview() {
		return review;
	}

	public Room getRoom() {
		return room;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setAdditionalServices(Set<PriceListItem> additionalServices) {
		this.additionalServices = additionalServices;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}