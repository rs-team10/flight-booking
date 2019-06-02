package com.tim10.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import com.tim10.domain.PriceListItem;
import com.tim10.domain.QuickRoomReservation;

public class QuickRoomResDTO {
	
	private Long id;
	private RoomDTO room;
	private Date dateFrom;
	private Date dateTo;
	private BigDecimal totalPrice;
	private Double discount;
	private Set<PriceListItem> additionalServices;
	
	public QuickRoomResDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QuickRoomResDTO(QuickRoomReservation res) {
		this.id = res.getId();
		this.room = new RoomDTO(res.getRoom());
		this.dateFrom = res.getDateFrom();
		this.dateTo = res.getDateTo();
		this.totalPrice = res.getTotalPrice();
		this.discount = res.getDiscount();
		this.setAdditionalServices(res.getAdditionalServices());
	}
	
	public QuickRoomResDTO(Long id, RoomDTO room, Date dateFrom, Date dateTo, BigDecimal totalPrice, Double discount) {
		super();
		this.id = id;
		this.room = room;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.totalPrice = totalPrice;
		this.discount = discount;
	}
	
	
	public Long getId() {
		return id;
	}
	public RoomDTO getRoom() {
		return room;
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
	public Double getDiscount() {
		return discount;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setRoom(RoomDTO room) {
		this.room = room;
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
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Set<PriceListItem> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(Set<PriceListItem> additionalServices) {
		this.additionalServices = additionalServices;
	}
	
	
}
