package com.tim10.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tim10.dto.NewRentACarDTO;

@Entity
@Table(name="RentACar")
public class RentACar extends Company {
	
	@OneToMany(mappedBy="id", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<BranchOffice> branchOffices;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<RentACarAdmin> administrators;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<QuickVehicleReservation> quickVehicleReservations;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	public PriceList additionalServicesPriceList;

	public RentACar() {
		branchOffices = new HashSet<BranchOffice>();
		additionalServicesPriceList = new PriceList();
		quickVehicleReservations = new HashSet<QuickVehicleReservation>();
		administrators = new HashSet<RentACarAdmin>();
	}
	
	public RentACar(NewRentACarDTO dto) {
		branchOffices = new HashSet<BranchOffice>();
		additionalServicesPriceList = new PriceList();
		quickVehicleReservations = new HashSet<QuickVehicleReservation>();
		this.setName(dto.getName());
		this.setLocation(dto.getLocation());
		administrators = dto.getAdministrators();
	}

	public Set<BranchOffice> getBranchOffices() {
		return branchOffices;
	}

	public Set<RentACarAdmin> getAdministrators() {
		return administrators;
	}

	public Set<QuickVehicleReservation> getQuickVehicleReservations() {
		return quickVehicleReservations;
	}

	public PriceList getAdditionalServicesPriceList() {
		return additionalServicesPriceList;
	}

	public void setBranchOffices(Set<BranchOffice> branchOffices) {
		this.branchOffices = branchOffices;
	}

	public void setAdministrators(Set<RentACarAdmin> administrators) {
		this.administrators = administrators;
	}

	public void setQuickVehicleReservations(Set<QuickVehicleReservation> quickVehicleReservations) {
		this.quickVehicleReservations = quickVehicleReservations;
	}

	public void setAdditionalServicesPriceList(PriceList additionalServicesPriceList) {
		this.additionalServicesPriceList = additionalServicesPriceList;
	}
}