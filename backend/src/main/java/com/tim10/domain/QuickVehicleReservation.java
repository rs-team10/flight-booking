package com.tim10.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class QuickVehicleReservation extends VehicleReservation {

	private static final long serialVersionUID = 1L;
	
	@Column(name="discount")
	private Double discount;
	
	

	public QuickVehicleReservation() {
		super();
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	
	public boolean filter(Date from, Date to) {
		
		Date rFrom = this.getDateFrom();
		Date rTo = this.getDateTo();

		boolean prov1 = (from.before(rFrom) && to.after(rFrom) ) || (from.before(rTo) && to.after(rTo)); //svi koji su delimicno rezervisani
		boolean prov2 = from.after(rFrom) && to.before(rTo); //ako je rezervacija u celom intervalu
		boolean prov3 = from.equals(rFrom) && to.equals(rTo);
				
		if( prov1 || prov2 ||  prov3)
			return true;
		else
			return false;

	}
}